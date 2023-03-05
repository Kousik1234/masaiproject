package com.masaiproject.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.masaiproject.Enum.CourseDurationType;
import com.masaiproject.Enum.CourseType;
import com.masaiproject.Enum.Mode;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String mainTitle;

	private String subTitle;

	private String courseDetails;

	private String paymentDetailsText;

	private Integer courseDuration;

	private LocalDate startDate;

	private LocalDate lastDate;

	private CourseType courseType;

	private Mode mode;



	private CourseDurationType courseDurationType;


	@ManyToMany
	@JoinTable(name = "course_user")
	private List<Users> users = new ArrayList<>();
}