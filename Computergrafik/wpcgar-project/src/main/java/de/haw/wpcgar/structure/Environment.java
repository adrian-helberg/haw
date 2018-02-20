package de.haw.wpcgar.structure;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * System to bundle seed and parameters.
 * @author Adrian Helberg
 */
public class Environment {
    private final String seed;
    private final Map<String, Parameter> params = new HashMap<>();
    private final Map<Class<? extends Parameter>, String> classes = new HashMap<>();

    public Environment(String seed) {
        this.seed = seed;
    }

    public String getSeed() {
        return seed;
    }

    public Map<String, Parameter> getParams() {
        return params;
    }

    public Parameter getParameter(String name)
    {
        return params.get(name);
    }

    @SuppressWarnings("unchecked")
    public <T extends Parameter> T getParam(Class<T> c)
    {
        return (T) getParameter(classes.get(c));
    }

    public <T extends Parameter> void registerParam(Class<T> c) {
        try {
            Constructor<T> constructor = c.getConstructor(String.class);
            T param = constructor.newInstance(seed);
            registerParam(param);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private <T extends Parameter> void registerParam(T param) {
        params.put(param.getName(), param);
        classes.put(param.getClass(), param.getName());
    }
}
