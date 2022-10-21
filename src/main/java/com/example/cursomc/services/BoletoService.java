package com.example.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pag, Date instateDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instateDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pag.setDataVencimento(cal.getTime());
	}
}
