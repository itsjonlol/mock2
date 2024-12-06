package vttp.ssf.assessment.eventmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.User;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;


@Controller

public class RegistrationController {
    @Autowired
    DatabaseService databaseService;
    

    // TODO: Task 6

    @GetMapping("/events/register/{eventid}")
	public String register(@PathVariable("eventid") Integer eventId,Model model) {
        User user = new User();
        model.addAttribute("user",user);
        // Event event = databaseService.getEventById(eventId);
        Event event = databaseService.getEventById2(eventId);
        model.addAttribute("event",event);
		return "view1";
	}

    @PostMapping("/events/register/{eventid}") // i want it to still be the same registration event id
    public String processRegistration(@PathVariable("eventid") Integer eventId,@Valid @ModelAttribute User user,BindingResult result,Model model,
    RedirectAttributes redirectAttributes) {
        // Event event = databaseService.getEventById(eventId);
        Event event = databaseService.getEventById2(eventId);
        model.addAttribute("event",event); // need to show back the model
        if (result.hasErrors()) {
            return "view1";
            
        }
        if (!databaseService.checkIfUserOver21(user) || databaseService.checkIfEventFull(event, user.getTicketsNumber())) {
            redirectAttributes.addFlashAttribute("userfail",user);
            redirectAttributes.addFlashAttribute("eventfail",event);
            return "redirect:/registration/registerfail"; //only works for redirecting
        }
        

        //logic for successful registration
        
        event.setParticipants(event.getParticipants() + user.getTicketsNumber());
        // databaseService.updateEventParticipation(event);
        databaseService.updateEventParticipation2(event);

        
        model.addAttribute("eventsuccess",event); // cannot use this if not redirecting
        return "view2";
        //to pass on attributes. BUT must be a redirect
        // redirectAttributes.addFlashAttribute("eventsuccess",event);
        // return "redirect:/registration/register";
       
        
    }
    

    // TODO: Task 7
    //IF YOU ARE PLANNING TO REDIRECT -> you must use redirectattributes
    @GetMapping("/registration/register")
    public String successPage(@ModelAttribute("eventsuccess") Event event,Model model) {
        
        model.addAttribute("eventsuccess",event);
        return "view2";
    }

    @GetMapping("/registration/registerfail") 
    public String errorPage(@ModelAttribute("eventfail")Event event, @ModelAttribute("userfail") User user,Model model) {
        
        boolean isUserOver21 = databaseService.checkIfUserOver21(user);
        boolean isEventFull = databaseService.checkIfEventFull(event, user.getTicketsNumber());
        model.addAttribute("isUserOver21",isUserOver21);
        model.addAttribute("isEventFull",isEventFull);
        return "view3";
    }

    //this also works. attribute is added to the model
    // @GetMapping("/registration/register")
    // public String successPage() {
    
    //     return "view2";
    // }

//     @PostMapping("/events/register")
// public String processRegistration(@ModelAttribute("user") User user, Model model) {
//     Event event = databaseService.getEventById(user.getEventId());
    
//     // Add event data to the model to be used in the view
//     model.addAttribute("event", event);

//     return "registration-success";  // Directly return the view name
// }

// @GetMapping("/registration-success")
// public String showSuccessPage(Model model) {
//     // The event will already be available in the model if passed from the previous method BECAUSE SAME REQUEST CYCLE
//     Event event = (Event) model.getAttribute("event");
//     // Continue processing logic with the event
//     return "success";
// }


   



   
}

