package edu.tcu.cd.hogwartsartifactsonline.artifact.converter;
import edu.tcu.cd.hogwartsartifactsonline.wizard.converter.WizardToWizardDtoConverter;
import org.springframework.core.convert.converter.Converter;
import edu.tcu.cd.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import org.springframework.stereotype.Component;
import edu.tcu.cd.hogwartsartifactsonline.artifact.artifact;

@Component

public class ArtifactToArtifactDtoConverter implements Converter<artifact, ArtifactDto> {


    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;
    public ArtifactToArtifactDtoConverter(WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;


}
    @Override
    public ArtifactDto convert(artifact source) {
        ArtifactDto artifactDto = new ArtifactDto(source.getId(),
                source.getName(),
                source.getDescription(),
                source.getImageURl(),
                source.getOwner() != null
                        ? this.wizardToWizardDtoConverter.convert(source.getOwner())
                        :null);


        return artifactDto;
    }
}
