package de.haw;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class VersionInfo {

    public static final String VERSION = "SNAPSHOT-2018-02-16_12-14-55";

    public static final ZonedDateTime BUILD_TIME = ZonedDateTime.parse("2018-02-16T12:14:55.964Z");
}
