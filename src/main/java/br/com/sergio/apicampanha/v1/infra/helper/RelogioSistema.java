package br.com.sergio.apicampanha.v1.infra.helper;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Classe criada para fazer o mock do LocalDate do Java8 e deve ser usada nas classes 
 * onde os testes unitários devem simular uma data específica 
 * 
 * @author Sérgio
 */
public class RelogioSistema {

	private static Clock clock = Clock.systemDefaultZone();
	private static ZoneId zoneId = ZoneId.systemDefault();
	
	public static LocalDateTime agora() {
		return LocalDateTime.now(getClock());
	}
	
	public static LocalDate hoje() {
		return LocalDate.now(getClock());
	}
	
	public static void fixeRelogioEm(LocalDateTime localDateTime){
		clock = Clock.fixed(localDateTime.atZone(zoneId).toInstant(), zoneId);
	}
	
	public static void fixeRelogioEm(LocalDate localDate){
		LocalDateTime localDateTime = localDate.atStartOfDay();
		fixeRelogioEm(localDateTime);
	}
	
	public static void usarRelogioZonaPadrao(){
		clock = Clock.systemDefaultZone();
	}
	
	private static Clock getClock() {
		return clock ;
	}
}

