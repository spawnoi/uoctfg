import static org.fest.assertions.Assertions.assertThat;
import static play.data.Form.form;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

import org.junit.Test;

import controllers.Application.Login;
import controllers.Candidate;

import play.mvc.Content;
import views.html.mainmenu;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
    	
        Content html = views.html.index.render("Your new application is ready.",mainmenu.render());
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }


}
