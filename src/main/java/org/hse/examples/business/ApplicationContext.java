package org.hse.examples.business;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Создаёт необходимые для работы приложения объекты и обеспечивает к ним доступ
 */
public interface ApplicationContext {
    /**
     * Возвращает контекст приложения
     */
    static ApplicationContext getContext() {
        return new ApplicationContextImpl();
    }

    /**
     * Возвращает объект из контекста приложения
     *
     * @param name  имя объекта
     * @param clazz ссылка на тип объекта
     * @param <T>   тип объекта
     * @return объект заданного типа, обёрнуты в {@link Optional}
     */
    <T> Optional<T> getInstance(String name, Class<T> clazz);
}

class ApplicationContextImpl implements ApplicationContext {
    private final Map<String, Object> context = new HashMap<>();

    ApplicationContextImpl() {
        BiFunction<Predicate<Integer>, Integer, Calculator> simpleCalculatorConstructor = CalculatorImpl::new;
        BiFunction<Predicate<Integer>, Integer, Calculator> streamCalculatorConstructor = CalculatorStreamImpl::new;

        CalculatorFactory simpleCalculatorFactory = new GeneralCalculatorFactory(simpleCalculatorConstructor);
        CalculatorFactory streamCalculatorFactory = new GeneralCalculatorFactory(streamCalculatorConstructor);

        context.put("simple6DigitsCalculator", simpleCalculatorFactory.create(6));
        context.put("simple8DigitsCalculator", simpleCalculatorFactory.create(8));
        context.put("stream6DigitsCalculator", streamCalculatorFactory.create(6));
        context.put("stream8DigitsCalculator", streamCalculatorFactory.create(8));
    }

    @Override
    public <T> Optional<T> getInstance(String name, Class<T> clazz) {
        if (context.containsKey(name) && clazz.isAssignableFrom(context.get(name).getClass())) {
            return Optional.of((T) context.get(name));
        }

        return Optional.empty();
    }
}
