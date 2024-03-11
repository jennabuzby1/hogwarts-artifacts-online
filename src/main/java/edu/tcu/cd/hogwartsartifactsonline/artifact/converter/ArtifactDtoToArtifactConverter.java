package edu.tcu.cd.hogwartsartifactsonline.artifact.converter;

import edu.tcu.cd.hogwartsartifactsonline.artifact.artifact;
import edu.tcu.cd.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component

public class ArtifactDtoToArtifactConverter implements Converter<ArtifactDto, artifact> {
    @Override
    public artifact convert(ArtifactDto source) {
        artifact artifact = new artifact();
        artifact.setId(source.id());
        artifact.setName(source.name());
        artifact.setDescription(source.description());
        artifact.setImageURl(source.imageUrl());

        return artifact;
    }
}
