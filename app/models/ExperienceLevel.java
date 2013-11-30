package models;


import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;


@Entity
public class ExperienceLevel extends Model{
	
	private static final long serialVersionUID = 1L;

	@Id
    public Long id;
    
    @Constraints.Required
    public String name;
    
    /**
     * Generic query helper for entity Company with id Long
     */
    public static Model.Finder<Long,ExperienceLevel> find = new Model.Finder<Long,ExperienceLevel>(Long.class, ExperienceLevel.class);

    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(ExperienceLevel c: ExperienceLevel.find.orderBy("name").findList()) {
            options.put(c.id.toString(), c.name);
        }
        return options;
    }

	
}