package com.example.candidate_test_specification;

import com.example.candidate_test_specification.web.controllers.ProductControllerTest;
import com.example.candidate_test_specification.web.controllers.UserControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;



@RunWith(Suite.class)
@Suite.SuiteClasses({
        ProductControllerTest.class,
        UserControllerTest.class
})
class CandidateTestSpecificationApplicationTests {

}
