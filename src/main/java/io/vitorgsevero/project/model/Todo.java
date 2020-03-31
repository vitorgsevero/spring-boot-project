package io.vitorgsevero.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Todo extends AbstractEntity{

    @NotEmpty(message = "The field description is required")
    private String description;

    @NotEmpty(message = "The field completed is required")
    private Boolean completed;

    @JsonFormat(pattern = "yyyy-mm-dd")
	@Column(updatable = false)
	private Date created_At;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date updated_At;


}
