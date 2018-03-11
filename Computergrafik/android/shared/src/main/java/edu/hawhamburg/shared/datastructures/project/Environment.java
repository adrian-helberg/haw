package de.haw.wpcgar.structure;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * System to bundle seed and parameters.
 * @author Adrian Helberg
 */
public class Environment {
    private final String                                  seed;
    private final Map<String, Parameter>                  parameters = new HashMap<>();
    private final Map<Class<? extends Parameter>, String> classes    = new HashMap<>();

    public Environment(String seed)
    {
        this.seed = seed;
    }

    public Map<String, Parameter> getParameters()
    {
        return parameters;
    }

    @SuppressWarnings("unchecked")
    public <T extends Parameter> T getParameter(Class<T> c)
    {
        return (T) getParameter(classes.get(c));
    }

    public Parameter getParameter(String name)
    {
        return parameters.get(name);
    }

    public <T extends Parameter> void registerParameter(Class<T> c)
    {
        try
        {
            Constructor<T> constructor = c.getConstructor(String.class);
            T parameter = constructor.newInstance(seed);
            registerParameter(parameter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private <T extends Parameter> void registerParameter(T parameter)
    {
        parameters.put(parameter.getName(), parameter);
        classes.put(parameter.getClass(), parameter.getName());
    }
}
