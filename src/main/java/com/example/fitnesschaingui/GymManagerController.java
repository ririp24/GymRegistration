package com.example.fitnesschaingui;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for the Gym Manager GUI.
 * Handles user inputs to manage the member database and fitness classes.
 * Handles onClick functionality for GUI Nodes.
 * @author Albert Zou, Rishabh Patel
 */
public class GymManagerController {
    private MemberDatabase db;
    private ClassSchedule classes;

    @FXML
    private TextArea ta;
    @FXML
    private TextField memberFName;
    @FXML
    private TextField memberLName;
    @FXML
    private DatePicker memberDOB;
    @FXML
    private TextField memberLocation;
    @FXML
    private RadioButton memberStan;
    @FXML
    private RadioButton memberFam;
    @FXML
    private RadioButton memberPrem;
    @FXML
    private TextField className;
    @FXML
    private TextField classInstructor;
    @FXML
    private TextField classLocation;
    @FXML
    private TextField classFName;
    @FXML
    private TextField classLName;
    @FXML
    private DatePicker classDOB;
    @FXML
    private CheckBox classGuest;

    /**
     * Constructs GymManagerController object.
     * Creates new member database and fitness class objects.
     */
    public GymManagerController() {
        db = new MemberDatabase();
        classes = new ClassSchedule();
    }

    /**
     * Facilitates method to add a new member.
     * Performs input validation and handles output to user.
     * Ensures that all locations, dates, and names are valid from GUI input fields.
     */
    @FXML
    protected void onAddButtonClick() {
        if ( !memberValidate() ) {
            return;
        }
        String fName = memberFName.getText();
        String lName = memberLName.getText();
        Date date = new Date(Date.convertFromLocal(memberDOB.getValue().toString()));
        String location = memberLocation.getText();
        Location validatedLoc = null;
        boolean validLoc = false;
        for (Location loc : Location.values()) {
            if ( loc.name().equals(location.toUpperCase()) ) {
                validLoc = true;
                validatedLoc = loc;
                break;
            }
        }

        if ( !validLoc ) {
            ta.appendText(location + ": invalid location!\n");
            return;
        } else if ( !memberStan.isSelected() && !memberFam.isSelected()
                && !memberPrem.isSelected()) {
            ta.appendText("Please choose a membership type!\n");
            return;
        }

        Member m = null;
        if ( memberStan.isSelected() ) {
            m = new Member(fName, lName, date, validatedLoc);
        } else if ( memberFam.isSelected() ) {
            m = new Family(fName, lName, date, validatedLoc);
        } else {
            m = new Premium(fName, lName, date, validatedLoc);
        }

        if ( !m.isValidDOB() ) {
            ta.appendText("DOB " + date + ": cannot be today or a future date!\n");
        } else if ( !m.isAdult() ) {
            ta.appendText("DOB " + date + ": must be 18 or older to join!\n");
        } else if ( !db.add(m)) {
            ta.appendText(fName + " " + lName + " is already in the database.\n");
        } else {
            ta.appendText(fName + " " + lName + " added.\n");
        }
    }

    /**
     * Facilitates method to remove an existing member.
     * Prints appropriate user message regarding whether the member
     * was removed or not.
     * If the member wasn't removed, it is because they didn't exist
     * in the database.
     */
    @FXML
    protected void onRemoveButtonClick() {
        if ( !memberValidate() ) return;
        String fName = memberFName.getText();
        String lName = memberLName.getText();
        Date date = new Date(Date.convertFromLocal(memberDOB.getValue().toString()));

        Member m = new Member(fName, lName, date, null, null);
        if ( !db.remove(m) ) {
            ta.appendText(fName + " " + lName + " is not in the database.\n");
        } else {
            ta.appendText(fName + " " + lName + " removed.\n");
        }
    }

    /**
     * Validates members inputted in the Membership tab.
     * Checks if the member has a valid name and date.
     * @return true if member is valid, false otherwise.
     */
    protected boolean memberValidate() {
        if ( memberFName.getText().equals("") || memberLName.getText().equals("") ) {
            ta.appendText("Please enter both a first and last name.\n");
            return false;
        } else if ( memberDOB.getValue() == null ) {
            ta.appendText("Please pick a date!\n");
            return false;
        }
        return true;
    }

    /**
     * Validates class inputs in the Fitness Class tab.
     * Checks if the inputs are existing and valid.
     * @return true if inputs are valid, false otherwise.
     */
    protected boolean classValidate() {
        String fName = classFName.getText();
        String lName = classLName.getText();
        String instructor = classInstructor.getText();
        String course = className.getText();
        String location = classLocation.getText();

        if ( classDOB.getValue() == null ) {
            ta.appendText("Please pick a date!\n");
            return false;
        }

        String date = Date.convertFromLocal(classDOB.getValue().toString());
        Member m = new Member(fName, lName, new Date(date), null, null);
        boolean validLoc = false;
        for (Location loc : Location.values()) {
            if ( loc.name().equals(location.toUpperCase()) ) {
                validLoc = true;
                break;
            }
        }
        if ( !validLoc ) {
            ta.appendText(location + ": invalid location!\n");
            return false;
        }
        if ( classFName.getText().equals("") || classLName.getText().equals("") ) {
            ta.appendText("Please enter both a first and last name.\n");
            return false;
        }

        m = db.getMemberData(m);
        FitnessClass c = classes.getClass(course, instructor,
                Location.valueOf(location.toUpperCase()));
        if ( !(new Date(date)).isValid() ) {
            ta.appendText("DOB " + date + " invalid calendar date!\n");
        } else if (m == null ) {
            ta.appendText(fName + " " + lName + " " + date + " is not in the database.\n");
        } else if ( m.isExpired() ) {
            ta.appendText(fName + " " + lName + " " + date + " membership expired.\n");
        } else if ( !classes.instructorExists(instructor)) {
            ta.appendText(instructor + " - instructor does not exist\n");
        } else if ( !classes.classExists(course)) {
            ta.appendText(course + " - class does not exist.\n");
        } else if ( c == null ) {
            ta.appendText(course + " by " + instructor + " does not exist at " + location + "\n");
        } else {
            return true;
        }
        return false;
    }

    /**
     * Facilitates method to check an existing member into a fitness class.
     * Performs input validation and handles output to user.
     * Ensures that all classes, names, and dates are valid.
     */
    @FXML
    protected void onCheckInClick() {
        if ( !classValidate() ) {
            return;
        }
        String fName = classFName.getText();
        String lName = classLName.getText();
        String instructor = classInstructor.getText();
        String course = className.getText();
        String location = classLocation.getText();
        String date = Date.convertFromLocal(classDOB.getValue().toString());
        boolean guest = classGuest.isSelected();
        Member m = new Member(fName, lName,
                new Date(date), null, null);
        FitnessClass c = classes.getClass(course, instructor,
                Location.valueOf(location.toUpperCase()));
        m = db.getMemberData(m);

        if ( !guest ) {
            if (!c.validLoc(m)) {
                ta.appendText(fName + " " + lName + " " + "checking in "
                        + Location.valueOf(location.toUpperCase())
                        + " - standard membership location restriction.\n");
            } else if (classes.isTimeConflict(m, c) != null) {
                ta.appendText("Time conflict - " + c.fullName() + "\n");
            } else if (!c.checkIn(m)) {
                ta.appendText(fName + " " + lName + " already checked in.\n");
            } else {
                ta.appendText(fName + " " + lName + " checked in " + c + "\n");
            }
        }
        else {
            if ( !(m instanceof Family) ) {
                ta.appendText(("Standard membership - guest check-in is not allowed.\n"));
            } else if (!c.validGuestLoc(m) ) {
                ta.appendText(fName + " " + lName + " Guest checking in "
                        + Location.valueOf(location.toUpperCase()) + " - guest location restriction.\n");

            } else if ( !c.checkInGuest(m) ) {
                ta.appendText(fName + " " + lName + " ran out of guest pass.\n");
            } else {
                ta.appendText((fName + " " + lName + " (guest) checked in " + c + "\n"));
            }
        }


    }

    /**
     * Facilitates method to check out an existing member from a fitness class.
     * Performs input validation and handles output to user.
     * Ensures that all classes, names, and dates are valid.
     */
    @FXML
    protected void onDoneClick() {
        if ( !classValidate() ) {
            return;
        }
        String fName = classFName.getText();
        String lName = classLName.getText();
        String instructor = classInstructor.getText();
        String course = className.getText();
        String location = classLocation.getText().toUpperCase();
        String date = Date.convertFromLocal(classDOB.getValue().toString());
        boolean guest = classGuest.isSelected();
        Member m = new Member(fName, lName,
                new Date(date), null, null);
        FitnessClass c = classes.getClass(course, instructor,
                Location.valueOf(location.toUpperCase()));
        m = db.getMemberData(m);

        if ( !guest ) {
            if ( !c.done(m) ) {
                ta.appendText(fName + " " + lName
                        + " did not check in.\n");
            }
            else {
                ta.appendText(fName + " " + lName
                        + " done with the class.\n");
            }
        } else {
            if ( !(m instanceof Family) ) {
                ta.appendText("Standard membership - guest sign-out is not allowed.\n");
            } else if ( !c.doneGuest(m)) {
                ta.appendText(fName + " " + lName + "ran out of guest pass.\n");
            } else {
                ta.appendText(fName + " "
                        + lName + " Guest done with the class.\n");
            }
        }
    }

    /**
     * Prints current member list.
     */
    @FXML
    protected void onPrintClick() {
        ta.appendText(db.getPrint() + "\n");
    }

    /**
     * Prints member list sorted by county and leaves databases sorted.
     */
    @FXML
    protected void onPrintByCountyClick() {
        ta.appendText(db.getPrintByCounty() + "\n");
    }

    /**
     * Prints member list sorted by last, then first name
     * and leaves databases sorted.
     */
    @FXML
    protected void onPrintByNameClick() {
        ta.appendText(db.getPrintByName() + "\n");
    }

    /**
     * Prints member list sorted by expiration date
     * and leaves databases sorted.
     */
    @FXML
    protected void onPrintByExpirationClick() {
        ta.appendText(db.getPrintByExpiration() + "\n");
    }

    /**
     * Loads and prints the loaded members from the file.
     * Calls the loadMembers() function of the MemberDatabase class.
     * Prints appropriate output headers for the list of members.
     */
    @FXML
    protected void onLoadMembersClick() {
        db.loadMembers();
        ta.appendText("\n-list of members loaded-\n"
                + db.toString() + "\n-end of list-\n");
    }

    /**
     * Prints class schedule.
     */
    @FXML
    protected void onPrintClassesClick() {
        if ( classes.isEmpty() ) {
            ta.appendText(classes.toString() + "\n");
        } else {
            ta.appendText("\n-Fitness classes-\n"
                    + classes.toString() + "\n-end of class list-\n");
        }
    }

    /**
     * Loads and prints the loaded schedule from the file.
     * Calls the loadSchedule() function of the ClassSchedule class.
     * Prints appropriate output headers for the list of classes.
     */
    @FXML
    protected void onLoadClassesClick() {
        classes.loadSchedule();
        ta.appendText("\n-Fitness classes loaded-\n"
                + classes.toString() + "\n-end of class list-\n");
    }

    /**
     * Prints the member database with fees (including starting fees).
     */
    @FXML
    protected void onFirstBillClick() {
        if ( db.isEmpty() ) {
            ta.appendText(db.toStringWithFees(true) + "\n");
        } else {
            ta.appendText("\n-list of members with first bill membership fees-\n"
                    + db.toStringWithFees(true) + "\n-end of list-\n");
        }
    }

    /**
     * Prints the member database with just recurring fees.
     */
    @FXML
    protected void onNextBillClick() {
        if ( db.isEmpty() ) {
            ta.appendText(db.toStringWithFees(false) + "\n");
        } else {
            ta.appendText("\n-list of members with next bill membership fees-\n"
                    + db.toStringWithFees(false) + "\n-end of list-\n");
        }
    }
}
