package wmi.amu.edu.pl.pri;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Blob;

@Converter
public abstract class ContentConverter implements AttributeConverter<byte[], Blob> {

}
