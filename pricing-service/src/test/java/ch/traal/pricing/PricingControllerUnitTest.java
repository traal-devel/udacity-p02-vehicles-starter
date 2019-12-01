package ch.traal.pricing;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ch.traal.pricing.api.PricingController;
import ch.traal.pricing.service.PricingService;

@RunWith(SpringRunner.class)
@WebMvcTest(PricingController.class)
public class PricingControllerUnitTest {

  
  /* constants */
  private Logger logger = LogManager.getLogger(PricingControllerUnitTest.class);
  
  /* member variables */
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PricingService pricingService;
  
  /* constructors */
  public PricingControllerUnitTest() {
    super();
  }

  
  /* methods */
  @Test
  public void testNotNull() throws Exception {
    assertNotNull(this.pricingService);
  }
  
  @Test
  public void getPriceForVehicleId_1_List() throws Exception {
    Long vehicleId = 1L;
    
    this.mockMvc.perform(
                  get("/services/price")
                    .param("vehicleId", Long.toString(vehicleId))
                )
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("{}"))
                ;
    // :INFO: Verify method checks if in the pricingService the methods was 
    //        invoked once with the value of the variable vehiclieID
    verify(pricingService, times(1)).getPrice(vehicleId);
  }

}
