package vn.triplet.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.triplet.controller.web.BaseController;
import vn.triplet.model.Order.Status;
import vn.triplet.model.User;
import vn.triplet.model.User.Role;
import vn.triplet.service.OrderService;

@PropertySource("classpath:messages.properties")
@Controller
@RequestMapping("/dashboard")
public class HomeController extends BaseController {

	@Autowired
	OrderService orderService;

	@Value("${messages.notAlow}")
	private String msg_notAlow;

	@RequestMapping(value = { "", "/" })
	public String index(Model model, HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		User user = loadCurrentUser(request);
		if(user==null) {
			return "redirect:/login";
		}
		if(user.getRole()!= Role.ADMIN) {
			redirectAttributes.addFlashAttribute("msg", msg_notAlow);
			return "redirect:/";
		}
		model.addAttribute("totalRevenue", orderService.getTotalRevenue());
		model.addAttribute("successOrderNum", orderService.loadOrdersByStatus(Status.ACCEPTED).size());
		model.addAttribute("pendingOrderNum", orderService.loadOrdersByStatus(Status.PENDING).size());
		model.addAttribute("customerNum", userService.loadUsers(Role.USER).size());
		return "views/admin/home/index";
	}

}
