package edu.tcu.cd.hogwartsartifactsonline.artifact;

import com.azure.json.implementation.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cd.hogwartsartifactsonline.artifact.dto.ArtifactDto;
import edu.tcu.cd.hogwartsartifactsonline.artifact.utils.IdWorker;
import edu.tcu.cd.hogwartsartifactsonline.client.ai.chat.ChatClient;
import edu.tcu.cd.hogwartsartifactsonline.client.ai.chat.dto.ChatRequest;
import edu.tcu.cd.hogwartsartifactsonline.client.ai.chat.dto.ChatResponse;
import edu.tcu.cd.hogwartsartifactsonline.client.ai.chat.dto.Choice;
import edu.tcu.cd.hogwartsartifactsonline.client.ai.chat.dto.Message;
import edu.tcu.cd.hogwartsartifactsonline.wizard.Wizard;
import edu.tcu.cd.hogwartsartifactsonline.wizard.dto.WizardDto;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(value = "dev")

class artifactServiceTest {

    @Mock
    edu.tcu.cd.hogwartsartifactsonline.artifact.artifactRepository artifactRepository;

    @Mock
    IdWorker idWorker;

    @Mock
    ChatClient chatClient;

    @InjectMocks

    artifactService artifactService;

    List<artifact> artifacts;

    @BeforeEach
    void setUp() {
        this.artifacts = new ArrayList<>();

        artifact a1 = new artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        a1.setImageURl("ImageUrl");


        artifact a2 = new artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible");
        a2.setImageURl("ImageUrl");

        this.artifacts = new ArrayList<>();
        this.artifacts.add(a1);
        this.artifacts.add(a2);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {

        //Given. Arrange inputs and targets. Define the behavior of Mock object artifactRepository
        artifact a = new artifact();
        a.setId("1250808601744904192");
        a.setName("Invisibility Cloak");
        a.setDescription("An invisibility cloak is used t make the wearer invis");
        a.setImageURl("ImageUrl");

        Wizard w = new Wizard();
        w.setId(2);
        w.setName("Harry Potter");
        a.setOwner(w);

        given(artifactRepository.findById("125080860174490419")).willReturn(Optional.of(a)); // Defines the behavior of the Mock object


        // When. Act on the target behavior. When steps should cover the method to be tested.

        artifact returnedArtifact = artifactService.findById("125080860174490419");

        // Then. Assert expected outcomes.

        assertThat(returnedArtifact.getId()).isEqualTo(a.getId());
        assertThat(returnedArtifact.getName()).isEqualTo(a.getName());
        assertThat(returnedArtifact.getDescription()).isEqualTo(a.getDescription());
        assertThat(returnedArtifact.getImageURl()).isEqualTo(a.getImageURl());
        verify(artifactRepository, times(1)).findById("125080860174490419");
    }

    @Test
    void testFindByIdNotFound(){
        // Given
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() ->{
            artifact returnedArtifact = artifactService.findById("125080860174490419");
        });

        // Then

        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find artifact with Id 125080860174490419:(");
        verify(artifactRepository, times(1)).findById("125080860174490419");

    }

        @Test
        void testFindAllSuccess(){
            //Given

            given(artifactRepository.findAll()).willReturn(this.artifacts);

            //When

            List<artifact> actualArtifacts = artifactService.findAll();

            // Then

            assertThat(actualArtifacts.size()).isEqualTo(this.artifacts.size());
            verify(artifactRepository, times(1)).findAll();
        }

        @Test
        void testSaveSuccess(){
        //Given

       artifact newArtifact = new artifact();
       newArtifact.setName("Artifact 3");
       newArtifact.setDescription("Description....");
       newArtifact.setImageURl("ImageUrl...");

       given(idWorker.nextId()).willReturn(123456L);
       given(artifactRepository.save(newArtifact)).willReturn(newArtifact);

            //When

            artifact savedArtifact = artifactService.save(newArtifact);

            //Then

            assertThat(savedArtifact.getId()).isEqualTo("123456");
            assertThat(savedArtifact.getName()).isEqualTo(newArtifact.getName());
            assertThat(savedArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
            assertThat(savedArtifact.getImageURl()).isEqualTo(newArtifact.getImageURl());
            verify(artifactRepository, times(1)).save(newArtifact);
        }


        @Test
        void testUpdatedSuccess(){
        // Given
            artifact oldArtifact = new artifact();
            oldArtifact.setId("1250808601744904192");
            oldArtifact.setName("Invisibility Cloak");
            oldArtifact.setDescription("An invisibility cloak is used t make the wearer invis");
            oldArtifact.setImageURl("ImageUrl");

            artifact update = new artifact();

            // update.setId("1250808601744904192");
            update.setName("Invisibility Cloak");
            update.setDescription("An invisibility cloak is used t make the wearer invis");
            update.setImageURl("ImageUrl");

            given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(oldArtifact));
            given(artifactRepository.save(oldArtifact)).willReturn(oldArtifact);

           artifact updateArtifact = artifactService.update("1250808601744904192", update);

           assertThat(updateArtifact.getId()).isEqualTo("1250808601744904192");
           assertThat(updateArtifact.getDescription()).isEqualTo(update.getDescription());
           verify(artifactRepository, times(1)).findById("1250808601744904192");
           verify(artifactRepository, times(1)).save(oldArtifact);



            //When
        }

        @Test
        void testUpdateNotFound() {


        // Given

            artifact update = new artifact();

            update.setName("Invisibility Cloak");
            update.setDescription("An invisibility cloak is used t make the wearer invis");
            update.setImageURl("ImageUrl");

            given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());


            //When

            assertThrows(ObjectNotFoundException.class, () -> {
                artifactService.update("1250808601744904192", update);
            });



            // Then

            verify(artifactRepository, times(1)).findById("1250808601744904192");




        }

        @Test
        void testDeleteSuccess() {

            // Given

            artifact artifact = new artifact();
            artifact.setId("1250808601744904192");
            artifact.setName("Invisibility Cloak");
            artifact.setDescription("An invisibility cloak is used t make the wearer invisible.");
            artifact.setImageURl("ImageUrl");

            given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(artifact));
            doNothing().when(artifactRepository).deleteById("1250808601744904192");


            // When

            artifactService.delete("1250808601744904192");

            // Then

            verify(artifactRepository, times(1)).deleteById("1250808601744904192");




        }



    @Test
    void testDeleteNotFound() {

        // Given

        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());


        // When

        assertThrows(ObjectNotFoundException.class, () -> {
            artifactService.delete("1250808601744904192");
        });

        // Then

        verify(artifactRepository, times(1)).findById("1250808601744904192");

    }

    @Test
    void testSummarizeSuccess() throws JsonProcessingException {
        // Given:
        WizardDto wizardDto = new WizardDto(1, "Albus Dombledore", 2);
        List<ArtifactDto> artifactDtos = List.of(
                new ArtifactDto("1250808601744904191", "Deluminator", "A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.", "ImageUrl", wizardDto),
                new ArtifactDto("1250808601744904193", "Elder Wand", "The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.", "ImageUrl", wizardDto)
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonArray = objectMapper.writeValueAsString(artifactDtos);

        List<Message> messages = List.of(
                new Message("system", "Your task is to generate a short summary of a given JSON array in at most 100 words. The summary must include the number of artifacts, each artifact's description, and the ownership information. Don't mention that the summary is from a given JSON array."),
                new Message("user", jsonArray)
        );

        ChatRequest chatRequest = new ChatRequest("gpt-4", messages);

        ChatResponse chatResponse = new ChatResponse(List.of(
                new Choice(0, new Message("assistant", "A summary of two artifacts owned by Albus Dumbledore."))));

        given(this.chatClient.generate(chatRequest)).willReturn(chatResponse);

        // When:
        String summary = this.artifactService.summarize(artifactDtos);

        // Then:
        assertThat(summary).isEqualTo("A summary of two artifacts owned by Albus Dumbledore.");
        verify(this.chatClient, times(1)).generate(chatRequest);
    }

}