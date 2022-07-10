package at.orsystems.smartmirror.startup;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OpenWeatherMapProperties.class)
public class SmartMirrorConfiguration {
}
