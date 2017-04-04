package project.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project.persistence.entities.ScheduleItem;
import project.service.ScheduleService;

/**
 * Created by Svava on 19.11.16.
 */
public class ItemValidator implements Validator {

    private ScheduleService scheduleService = new ScheduleService();

    @Override
    public boolean supports(Class<?> aClass) {
        return ScheduleItem.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    }

}
