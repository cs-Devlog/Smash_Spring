package com.java.smash;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.java.smash.command.SCommand;
import com.java.smash.command.SConnectionListCommand;
import com.java.smash.command.SMedicChangepwdCommand;
import com.java.smash.command.SPatientAddCommand;
import com.java.smash.command.SPatientDeleteCommand;
import com.java.smash.command.SPatientDeviceListCommand;
import com.java.smash.command.SPatientListCommand;
import com.java.smash.command.SProgramAddCommand;
import com.java.smash.command.SProgramDeleteCommand;
import com.java.smash.command.SProgramListCommand;
import com.java.smash.dao.IConnectionDao;
import com.java.smash.dao.IProgramDao;
import com.java.smash.util.Constant;

@Controller
@RequestMapping("medic")
public class MedicController {

	public SCommand command;

	public JdbcTemplate template;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		Constant.template = template;
	}
	
	@Autowired
	private SqlSession sqlSession;

	@RequestMapping("Main")
	public String medicMain(HttpSession session, HttpServletRequest request, Model model) {
		// FlashMap으로 넘어온 정보
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

		if (map != null) {
			String id = (String) map.get("id");
			String pwd = (String) map.get("pwd");

			session.setAttribute("id", id);
			session.setAttribute("pwd", pwd);
		}
		return "medic/medic_main";
	}

	@RequestMapping("Patient")
	public String medicPatient(HttpServletRequest request, Model model) {
		command = new SPatientListCommand();
		command.execute(model);

		return "medic/medic_patient";
	}

	@RequestMapping("PatientAdd")
	public String medicPatientAdd(HttpServletRequest requset, Model model) {

		IProgramDao dao = sqlSession.getMapper(IProgramDao.class);
		model.addAttribute("list", dao.listDao());

		return "medic/medic_patient_add";
	}

	@RequestMapping(value = "PatientAddOK", method = RequestMethod.POST)
	public String medicPatientAddOk(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);

		command = new SPatientAddCommand();
		command.execute(model);

		return "redirect:Patient";
	}

	@RequestMapping("PatientEdit")
	public String medicPatientEdit(HttpServletRequest request, Model model) {
		// todo
		return "medkc/medic_patient_edit";
	}

	@RequestMapping("PatientDelete")
	public String medicPatientDelete(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);

		command = new SPatientDeleteCommand();
		command.execute(model);

		return "redirect:Patient";
	}

	@RequestMapping(value = "Connection", method = RequestMethod.GET)
	public String medicConnection(HttpServletRequest request, Model model) {
		
		IConnectionDao dao = sqlSession.getMapper(IConnectionDao.class);
		model.addAttribute("list", dao.listDao());
		
		return "medic/medic_connection";
	}

	@RequestMapping(value = "ConnectionStart", method = RequestMethod.POST)
	public String medicConnectionStart(HttpServletRequest request, Model model) {
		
		String patientNumber = request.getParameter("patientNumber");
		String deviceNumber = request.getParameter("deviceNumber");
		
		System.out.println(patientNumber);
		System.out.println(deviceNumber);
		
		IConnectionDao dao = sqlSession.getMapper(IConnectionDao.class);
		dao.connectionStartDao(patientNumber, deviceNumber);
		
		return "redirect:Connection";
	}
	
	@RequestMapping(value = "ConnectionStop", method = RequestMethod.POST)
	public String medicConnectionStop(HttpServletRequest request, Model model) {
		
		String deviceNumber = request.getParameter("deviceNumber");

		System.out.println(deviceNumber);

		IConnectionDao dao = sqlSession.getMapper(IConnectionDao.class);
		dao.connectionStopDao(deviceNumber);
		
		return "redirect:Connection";
	}

	@RequestMapping("Program")
	public String medicProgram(HttpServletRequest request, Model model) {
		
		IProgramDao dao = sqlSession.getMapper(IProgramDao.class);
		model.addAttribute("list", dao.listDao());

		return "medic/medic_program";
	}

	@RequestMapping("ProgramAdd")
	public String medicProgramAdd(HttpServletRequest requset, Model model) {

		return "medic/medic_program_add";
	}

	@RequestMapping(value = "ProgramAddOK", method = RequestMethod.POST)
	public String medicProgramAddOk(HttpServletRequest request, Model model) {

		String programNumber = request.getParameter("programNumber");
		String programName = request.getParameter("programName");
		String programContent = request.getParameter("programContent");
		String programDisease = request.getParameter("programDisease");

		IProgramDao dao = sqlSession.getMapper(IProgramDao.class); 
		dao.insertDao(programNumber, programName, programContent, programDisease);

		return "redirect:Program";
	}

	@RequestMapping("ProgramEdit")
	public String mediProgrameEdit(HttpServletRequest request, Model model) {

		String programNumber = request.getParameter("programNumber");

		IProgramDao dao = sqlSession.getMapper(IProgramDao.class); 
		model.addAttribute("list", dao.getDB(programNumber));
		
		return "medic/medic_program_edit";
	}
	
	@RequestMapping(value = "ProgramEditOK", method = RequestMethod.POST)
	public String medicProgramEditOk(HttpServletRequest request, Model model) {
		
		String programNumber = request.getParameter("programNumber");
		String programName = request.getParameter("programName");
		String programContent = request.getParameter("programContent");
		String programDisease = request.getParameter("programDisease");

		IProgramDao dao = sqlSession.getMapper(IProgramDao.class);
		dao.editDao(programName, programContent, programDisease, programNumber);

		return "redirect:Program";
	}

	@RequestMapping("ProgramDelete")
	public String medicExerciseDelete(HttpServletRequest request, Model model) {

		String programNumber = request.getParameter("programNumber");
		
		IProgramDao dao = sqlSession.getMapper(IProgramDao.class);
		dao.deleteDao(programNumber);

		return "redirect:Program";
	}

	@RequestMapping("changepwd")
	public String medicChangepwd(HttpServletRequest request, Model model) {
		model.addAttribute("status", 0);

		return "medic/medic_changepwd";
	}

	@RequestMapping("changepwdCheck")
	public String medicChangepwdCheck(HttpSession session, HttpServletRequest request, Model model) {
		String current_id = (String) session.getAttribute("id");

		model.addAttribute("current_id", current_id);
		model.addAttribute("request", request);

		command = new SMedicChangepwdCommand();
		command.execute(model);

		return "redirect:ischangepwd";
	}

	@RequestMapping("ischangepwd")
	public String medicischangepwd(HttpServletRequest request, Model model) {
		String status = (String) request.getParameter("status");

		if (Integer.parseInt(status) == 0) {
			return "medic/medic_main";
		} else {
			model.addAttribute("status", Integer.parseInt(status));
			return "medic/medic_changepwd";
		}
	}
}
