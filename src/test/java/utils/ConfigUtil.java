package utils;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config/config.properties"})
public interface ConfigUtil extends Config {

    @Config.Key("app.url")
    String url();

    @Config.Key("auto.browser")
    String browser();

    @Config.Key("auto.timeout")
    String timeout();

}
