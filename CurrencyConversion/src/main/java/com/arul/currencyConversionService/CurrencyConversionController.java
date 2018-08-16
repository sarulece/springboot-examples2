package com.arul.currencyConversionService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	private static Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionController.class);
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	@RequestMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
	{
		LOGGER.info("convertCurrency - Start");
		CurrencyConversionBean bean = null;
		try
		{
			/*Map<String, String> uriVariables = new HashMap<>();
			uriVariables.put("from", from);
			uriVariables.put("to", to);
			
			ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate()
					.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, uriVariables);
			bean = responseEntity.getBody();*/
			
			bean = proxy.retriveExchangeValue(from, to);
			LOGGER.info("response: " + bean);
		}catch(Exception e)
		{
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		LOGGER.info("convertCurrency - End");
		return new CurrencyConversionBean(bean.getId(), from, to,
				bean.getConversionMultiple(), quantity, quantity.multiply(bean.getConversionMultiple()) , bean.getPort());
	}
}
