import java.util.List;
import java.util.Map;

import models.UserApp;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {
    
    public void onStart(Application app) {
        InitialData.insert(app);
    }
    
    static class InitialData {
        
        public static void insert(Application app) {
            if(Ebean.find(UserApp.class).findRowCount() == 0) {
                
                @SuppressWarnings("unchecked")
				Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");

                Ebean.save(all.get("sectors"));
                Ebean.save(all.get("worktypes"));
                Ebean.save(all.get("experiences"));
                Ebean.save(all.get("studieslevels"));
                Ebean.save(all.get("provinces"));
                
                // Insert users first
                Ebean.save(all.get("users"));

                // Insert durations
                Ebean.save(all.get("durations"));
                
                // Insert jobs
                Ebean.save(all.get("jobs"));
                for(Object job: all.get("jobs")) {
                    // Insert the project/user relation
                    Ebean.saveManyToManyAssociations(job, "inscribed");
                }
                
                /*
                for(Object project: all.get("projects")) {
                    // Insert the project/user relation
                    Ebean.saveManyToManyAssociations(project, "members");
                }

                // Insert tasks
                Ebean.save(all.get("tasks"));
                */
                
            }
        }
        
    }
    
}