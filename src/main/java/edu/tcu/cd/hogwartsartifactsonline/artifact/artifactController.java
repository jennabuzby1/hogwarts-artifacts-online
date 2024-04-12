package edu.tcu.cd.hogwartsartifactsonline.artifact;

import edu.tcu.cd.hogwartsartifactsonline.artifact.converter.ArtifactDtoToArtifactConverter;
import edu.tcu.cd.hogwartsartifactsonline.artifact.converter.ArtifactToArtifactDtoConverter;
import edu.tcu.cd.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import edu.tcu.cd.hogwartsartifactsonline.system.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

import static edu.tcu.cd.hogwartsartifactsonline.artifact.StatusCode.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/artifacts")
public class artifactController {

    private final artifactService artifactService;

    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;

    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;


    public artifactController(edu.tcu.cd.hogwartsartifactsonline.artifact.artifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter, ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
        this.artifactDtoToArtifactConverter = artifactDtoToArtifactConverter;
    }

    @GetMapping("/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId) {

        artifact foundArtifact = this.artifactService.findById(artifactId);
        ArtifactDto artifactDto = this.artifactToArtifactDtoConverter.convert(foundArtifact);
        return new Result(true, SUCCESS, "Find One Success", artifactDto);


    }

    @GetMapping
    public Result findAllArtifacts() {
        List<artifact> foundArtifacts = this.artifactService.findAll();
        // Convert foundArtifact to a list of artifactsDtos

        List<ArtifactDto> artifactsDtos = foundArtifacts.stream()
                .map(this.artifactToArtifactDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, edu.tcu.cd.hogwartsartifactsonline.artifact.StatusCode.SUCCESS, "Find All Success", artifactsDtos);

    }

    @PostMapping()
    public Result addArtifact(@Valid @RequestBody ArtifactDto artifactDto) {

            artifact newArtifact = this.artifactDtoToArtifactConverter.convert(artifactDto);
            artifact savedArtifact = this.artifactService.save(newArtifact);
            ArtifactDto savedArtifactDto = this.artifactToArtifactDtoConverter.convert(savedArtifact);
            return new Result(true, edu.tcu.cd.hogwartsartifactsonline.artifact.StatusCode.SUCCESS, "Add Success", savedArtifactDto);
        }

        @PutMapping("/{artifactId}")
        public Result updateArtifact(@PathVariable String artifactId, @Valid @RequestBody ArtifactDto artifactDto) {
            artifact update = this.artifactDtoToArtifactConverter.convert(artifactDto);
           artifact updatedArtifact =  this.artifactService.update(artifactId, update);
           ArtifactDto updatedArtifactDto = this.artifactToArtifactDtoConverter.convert(updatedArtifact);
            return new Result(true, edu.tcu.cd.hogwartsartifactsonline.artifact.StatusCode.SUCCESS, "Update Success", updatedArtifactDto);
        }

        @DeleteMapping("/{artifactId}")
        public Result deleteArtifact(@PathVariable String artifactId) {
        this.artifactService.delete(artifactId);

        return new Result(true, edu.tcu.cd.hogwartsartifactsonline.artifact.StatusCode.SUCCESS, "Delete Success");

        }

}
