package com.itheima.pinda.common.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import com.itheima.pinda.utils.DateUtils;
import org.springframework.core.convert.converter.Converter;

import static com.itheima.pinda.utils.DateUtils.DEFAULT_DATE_FORMAT;

/**
 * 解决入参为 Date类型
 *
 */
public class String2LocalDateConverter extends BaseDateConverter<LocalDate> implements Converter<String, LocalDate> {

    protected static final Map<String, String> FORMAT = new LinkedHashMap(2);

    static {
        FORMAT.put(DateUtils.DEFAULT_DATE_FORMAT, "^\\d{4}-\\d{1,2}-\\d{1,2}$");
        FORMAT.put("yyyy/MM/dd", "^\\d{4}/\\d{1,2}/\\d{1,2}$");
    }

    @Override
    protected Map<String, String> getFormat() {
        return FORMAT;
    }

    @Override
    public LocalDate convert(String source) {
        return super.convert(source, (key) -> LocalDate.parse(source, DateTimeFormatter.ofPattern(key)));
    }

}

