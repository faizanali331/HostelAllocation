package com.telucko.Allocation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student {

    @Id
    private int enrollmentNo;

    @Column(unique = true, nullable = false)
    private String email;

    private String firstName;
    private String lastName;

    @Column(length = 15)
    private String mobileNo;

    private String gender;
    private String fatherName;

    @Column(length = 15)
    private String fatherMobileNo;

    private String category;
    private String pwd;
    private int courseId;
    private int departmentId;
    private String marksheetQualifyingExam;
    private double cgpa;
    private String orphanChildOfWidow;
    private String homeTown;
    private String permanentAddress;
    private String proofPermanentAddress;
    private String correspondenceAddress;
    private String nearestRailwayStationLpi;
    private int distance;
    private String nationalCadetCorps;
    private String nationalServiceScheme;
    private String sports;
    private String extraCurricularActivities;
    private String secondDegree;
    private String scholarship;
    private String legalAction;
    private String feesReceipt;
    private String pgDegreeMoreThan;
    private String nonProfessionalDegreeProgram;
    private String detained;

    // Points for hostel allotment criteria
    private int cgpaPoint;
    private int orphanPoint;
    private int coursePoint;
    private int nccPoint;
    private int nssPoint;
    private int sportsPoint;
    private int distancePoint;
    private int extraCurricularPoint;
    private int pwdPoint;
    private int totalPoint;
}
