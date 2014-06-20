package br.com.vendas.api.controller.geo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.state.State;
import br.com.vendas.services.impl.geo.StateServiceImpl;

@RequestMapping(value="/states")
@Controller
public class StateController {

	
	@Autowired
	private StateServiceImpl stateServiceImpl;

	@RequestMapping(value="getStates.json", method = RequestMethod.GET)
	public @ResponseBody Map<String,? extends Object> loadStates() {

		HashMap<String, List<State>> modelMap = new HashMap<String,List<State>>();
		modelMap.put("states", stateServiceImpl.findAll());
		return modelMap;
	}
}
