package com.project.vo;

import com.project.model.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestTemplateVO {
	
	private Location location;
	private Flight flight;
}
