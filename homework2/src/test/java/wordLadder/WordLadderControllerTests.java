package wordLadder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.Param;

import java.util.Stack;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordLadderControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Method: getUserList()
     */
    @Test
    public void testGetWordLadderDefault() throws Exception {
        mockMvc.perform(get("/wordLadder").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo("[\"world\"]")));
    }

    @Test
    public void testGetWordLadderRequest() throws Exception {
        String result = "[\"code\",\"cade\",\"cate\",\"date\",\"data\"]";
        mockMvc.perform(get("/wordLadder")
                .param("start","code")
                .param("end", "data")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo(result)));
    }
}
