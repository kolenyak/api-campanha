package br.com.sergio.apicampanha.v1;

//@Converter(autoApply = true)
public class LocalDateAttributeConverter /*implements AttributeConverter<LocalDate, Date>*/ {
	
   /* @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
    	return (locDate == null ? null : Date.valueOf(locDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
    	return (sqlDate == null ? null : sqlDate.toLocalDate());
    }*/
}
