package com.cst438.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.AssignmentListDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;

@RestController
public class AssignmentController {

	
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired	
	CourseRepository courseRepository;
	
	/*@GetMapping("/assignment")
	public AssignmentListDTO getallAssignments() {
	

		List<Assignment> assignments = (List<Assignment>) assignmentRepository.findAll();
		AssignmentListDTO result = new AssignmentListDTO();
		for (Assignment a: assignments) {
			result.assignments.add(new AssignmentListDTO.AssignmentDTO(a.getId(), a.getCourse().getCourse_id(), a.getName(), a.getDueDate().toString() , a.getCourse().getTitle()));
		}
		
		return result;
		
		
	}*/
	
	
	//adding assignments to the course, has a name and due date

	@PostMapping("/assignment")
	public AssignmentDTO createNewAssignment(@RequestBody  AssignmentDTO assignment ) {

		
	
		Course course =courseRepository.findById(assignment.courseId).orElse(null); 
			if(course != null) {
				Assignment b = new Assignment();
				b.setCourse(course);
				b.setDueDate(Date.valueOf(assignment.dueDate));
				b.setName(assignment.assignmentName);
				b = assignmentRepository.save(b);
				assignment.assignmentId = b.getId();
				return assignment;
			}else {
			  throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Invalid course id. ");
			}
		
		
	}

	
	//instructor can change the assignment name for the course
	@PutMapping("/assignment/{id}")
	public void  updateAssignment(@RequestParam("name") String newname,@PathVariable("id") int id){
		
		Assignment assignment = assignmentRepository.findById(id).orElse(null);
		//checks to see what assignment name you want to change in the course
		if(assignment !=null) {
			
			assignment.setName(newname);
			assignmentRepository.save(assignment);
			
		}else {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Invalid assignment id. ");
		}
		
	
	}
	
	// instructor can delete an assignment for the course(only if there are no grades for the assignment)
	@DeleteMapping("/assignment/{id}")
	public void deleteAssignment( @PathVariable("id") int id) {
		Assignment assignmentNew = assignmentRepository.findById(id).orElse(null);
		
		//checks to see how to delete 
		if(assignmentNew !=null) {
			if(assignmentNew.getAssignmentGrades().isEmpty()) {
				assignmentRepository.delete(assignmentNew);
			}else {
				throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment has grades. ");
			}
		}else {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Invalid assignment id. ");
		}
		
	}
	
}
