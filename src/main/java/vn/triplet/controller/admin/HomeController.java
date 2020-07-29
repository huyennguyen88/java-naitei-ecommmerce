package vn.triplet.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@PropertySource("classpath:messages.properties")
@Controller
@RequestMapping("/dashboard")
public class HomeController {

	@Value("${messages.notAlow}")
	private String msg_notAlow;

	private static final int ADMIN = 0;
	private static final int USER = 1;

	@RequestMapping(value = { "", "/" })
	public String index(HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") != null) {
			int role = (int) session.getAttribute("roleUser");
			if (role == ADMIN)
				return "views/admin/home/index";
			
			redirectAttributes.addFlashAttribute("msg", msg_notAlow);
			return "redirect:/";
		}

		redirectAttributes.addFlashAttribute("msg", msg_notAlow);
		return "redirect:/";

	}
}
