package com.web.curation.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class HospitalInfo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int internist;
	private int doctor;
	private boolean emergencyDay;
	private boolean emergencyNight;
	private boolean emergencyNightPhone;
	private String sunStart;
	private String sunFinish;	
	private String monStart;
	private String monFinish;
	private String tueStart;
	private String tueFinish;
	private String wedStart;
	private String wedFinish;
	private String thuStart;
	private String thuFinish;
	private String friStart;
	private String firFinish;
	private String satStart;
	private String satFinish;
}
