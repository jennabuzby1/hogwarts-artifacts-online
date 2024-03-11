package edu.tcu.cd.hogwartsartifactsonline.wizard.converter;

import edu.tcu.cd.hogwartsartifactsonline.wizard.dto.WizardDto;
import edu.tcu.cd.hogwartsartifactsonline.wizard.Wizard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component

public class WizardToWizardDtoConverter implements Converter<Wizard, WizardDto>{

    @Override
    public edu.tcu.cd.hogwartsartifactsonline.wizard.dto.WizardDto convert(Wizard source) {
        WizardDto wizardDto= new WizardDto(source.getId(),
                                          source.getName(),
                                          source.getNumberOfArtifacts());
        return wizardDto;
    }
    }

