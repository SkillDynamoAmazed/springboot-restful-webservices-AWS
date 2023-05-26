package net.javaguides.springboot.controller;


import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import jakarta.validation.Valid;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AmazonSQS sqsClient;


    private String queueUrl = "https://sqs.ap-southeast-2.amazonaws.com/986164352203/Myawsqueue";

  @PostMapping("/createmessages")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(queueUrl, message);
        sqsClient.sendMessage(sendMessageRequest);
        return ResponseEntity.ok("Message sent successfully");
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> receiveMessages() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/deletemessages")
    public ResponseEntity<User> deleteMessages(){
        List<Message> messages = sqsClient.receiveMessage(queueUrl).getMessages();
        for (Message m : messages) {
            sqsClient.deleteMessage(queueUrl, m.getReceiptHandle());
        }
        return new  ResponseEntity<User>(HttpStatus.OK);
    }


    //ADDING @VALID ANNOTAION FOR API VALIDATION.
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user){
        UserDto usersaved = userService.createUser(user);
       return new  ResponseEntity<>(usersaved,HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId){
        UserDto user1 = userService.getUserById(userId);
        return new  ResponseEntity<>(user1,HttpStatus.OK);
    }
    @GetMapping("all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List users = userService.findAllUsers();
        return new  ResponseEntity<List<UserDto>>(users,HttpStatus.OK);
    }


    @PutMapping("update/{id}")
    public ResponseEntity<UserDto>UpdateUserById(@PathVariable("id") Long userId,@RequestBody @Valid UserDto user){
        UserDto updatedUser = userService.updateUser(user,userId);
        return new  ResponseEntity<UserDto>(updatedUser,HttpStatus.OK);
    }




    @DeleteMapping("delete/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") Long id){
         userService.deleteUserById(id);
        return new  ResponseEntity<User>(HttpStatus.OK);
    }


//    @EXCEPTIONHANDLER TO HANDLE SPECIFIC EXCEPTION AND RETURN CUSTOM ERROR BACK TO CLIENT
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails>handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
//                                                                       WebRequest webRequest){
//        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
//                resourceNotFoundException.getMessage(),
//                webRequest.getDescription(false),
//                "UISER_NOT_FOUND");
//        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
//    }
//
//


}
