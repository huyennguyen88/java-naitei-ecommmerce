package vn.triplet.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.triplet.model.Order;
import vn.triplet.model.Order.Status;
import vn.triplet.service.OrderService;

@PropertySource("classpath:messages.properties")
@Controller(value = "orderControllerOfadmin")
@RequestMapping("/admin/orders")
public class OrderController {

	@Autowired
	OrderService orderService;

	@Value("${messages.notFound}")
	private String msg_notFound;

	@Value("${order.status.notChange}")
	private String stt_notChange;

	@Value("${order.status.changed}")
	private String stt_changed;

	@GetMapping(value = { "", "/" })
	public String index(Model model) {
		List<Order> orders = orderService.loadOrders();
		model.addAttribute("orders", orders);
		return "views/admin/order/orders";
	}

	@GetMapping("/type")
	public String showOrderByStatus(@RequestParam int stt, Model model, final RedirectAttributes redirectAttributes) {
		List<Order> orders = orderService.loadOrdersByStatus(stt);
		model.addAttribute("orders", orders);
		return "views/admin/order/orders";
	}

	@RequestMapping("/{id}/type")
	public String changeOrderStatus(@PathVariable("id") int id, @RequestParam int action, Model model,
			final RedirectAttributes redirectAttributes) {
		String typeCss = "error";
		String message = stt_notChange;
		Status status = Status.REJECTED;

		Order order = orderService.findById(id);

		if (order == null) {
			redirectAttributes.addFlashAttribute("css", typeCss);
			redirectAttributes.addFlashAttribute("msg", msg_notFound);
			return "redirect:/admin/orders";
		}

		Status currentStatus = order.getStatus();
		if (currentStatus != Status.PENDING) {
			redirectAttributes.addFlashAttribute("css", typeCss);
			redirectAttributes.addFlashAttribute("msg", message);
			return "redirect:/admin/orders/"+order.getId();
		}

		if (action == 0)
			status = Status.ACCEPTED;
		order.setStatus(status);

		if (orderService.saveOrUpdate(order) == null) {
			redirectAttributes.addFlashAttribute("css", typeCss);
			redirectAttributes.addFlashAttribute("msg", message);
			return "redirect:/admin/orders/"+order.getId();
		}

		typeCss = "sucsess";
		message = stt_changed;
		redirectAttributes.addFlashAttribute("css", typeCss);
		redirectAttributes.addFlashAttribute("msg", message);
		return "redirect:/admin/orders/"+order.getId();

	}

	@RequestMapping("/{id}")
	String showOrderDetail(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		Order order = orderService.findById(id);
		if (order != null) {
			model.addAttribute("order", order);
			return "views/admin/order/order";

		}
		redirectAttributes.addFlashAttribute("css", "error");
		redirectAttributes.addFlashAttribute("msg", msg_notFound);
		return "redirect:/admin/orders";

	}

}
