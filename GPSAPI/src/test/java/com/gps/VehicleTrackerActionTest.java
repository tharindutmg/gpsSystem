package com.gps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.reflect.TypeToken;
import com.gps.action.VehicleTrackerAction;
import com.gps.domain.VehicleTracker;
import com.gps.exception.ResourceNotFoundException;
import com.gps.repository.VehicaleTrackingRepositary;

@RunWith(SpringRunner.class)
@WebMvcTest(VehicleTrackerAction.class)
public class VehicleTrackerActionTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	VehicaleTrackingRepositary vehicleRepository;
	
	private final String URL = "api/vehicle/";
	
	@Test
	public void testGetVehicle() throws Exception {

		// prepare data and mock's behaviour
		Long vehicleId=(long) 001;
		VehicleTracker vehicleTracker = new VehicleTracker(vehicleId, "test", "test", "test", 12.0,13.0);
		when(vehicleRepository.findById(any(Long.class)).orElseThrow(() -> new ResourceNotFoundException("VehicleTracker", "id", vehicleId))).thenReturn(vehicleTracker);

		// execute
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "{id}", new Long(1)).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(vehicleRepository).findById(any(Long.class));

		VehicleTracker resultVehicle = TestUtils.jsonToObject(result.getResponse().getContentAsString(), VehicleTracker.class);
		assertNotNull(resultVehicle);
		assertEquals(1l, resultVehicle.getVehicleId().longValue());
	}

	

	@Test
	public void testGetAllVehicle() throws Exception {

		// prepare data and mock's behaviour
		List<VehicleTracker> vehicleLst = buildVehicles();
		when(vehicleRepository.findAll()).thenReturn(vehicleLst);

		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();

		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);

		// verify that service method was called once
		verify(vehicleRepository).findAll();

		// get the List<VehicleTracker> from the Json response
		TypeToken<List<VehicleTracker>> token = new TypeToken<List<VehicleTracker>>() {
		};
		@SuppressWarnings("unchecked")
		List<VehicleTracker> vehiListResult = TestUtils.jsonToList(result.getResponse().getContentAsString(), token);

		assertNotNull("VehicleTracker not found", vehiListResult);
		assertEquals("Incorrect VehicleTracker List", vehicleLst.size(), vehiListResult.size());

	}

	

	private List<VehicleTracker> buildVehicles() {
		
		VehicleTracker v1 = new VehicleTracker((long) 001, "test", "test", "test", 12.0,13.0);
		VehicleTracker v2 = new VehicleTracker((long) 002, "test2", "test2", "test2", 14.0,15.0);
		
		List<VehicleTracker> empVehicle = Arrays.asList(v1, v2);
		return empVehicle;
	}
}
