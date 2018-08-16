package com.arul.forexService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ForexServiceController {
	private static Logger LOGGER = LoggerFactory.getLogger(ForexServiceController.class);

	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueRepository exchangeValueRepo;
	
	@RequestMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue getExchangeValue(@PathVariable String from, @PathVariable String to)
	{
		LOGGER.info("getExchangeValue - Start");
		ExchangeValue exchangeValue = null;
		try
		{
			exchangeValue = exchangeValueRepo.findByFromAndTo(from, to);
			int port = Integer.parseInt(environment.getProperty("server.port"));
			exchangeValue.setPort(port);
		}catch(Exception e)
		{
			LOGGER.error(e.getMessage());
		}
		LOGGER.info("getExchangeValue - End");
		return exchangeValue;
	}
	
}
