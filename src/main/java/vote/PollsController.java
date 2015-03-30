package vote;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * @author Ashish Bende
 */

@RestController
@RequestMapping("/api/v1")
@Repository
@ComponentScan
public class PollsController {

	@Autowired
	private PollsRepository pollrepo;

	final AtomicInteger counter = new AtomicInteger(1983456);



	@JsonView(Display.WithoutResult.class)
	@RequestMapping(value="/moderators/{moderator_id}/polls", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Polls pollCreate(@PathVariable(value="moderator_id") String id,@RequestBody @Valid  Polls polls){
		String moderator_id = id;
		String question = polls.getQuestion();
		String started_at = polls.getStarted_at();
		String expired_at = polls.getExpired_at();
		String[] choice = polls.getChoice();
		String poll_id = Integer.toHexString(counter.getAndIncrement());
		Polls poll_object = new Polls(poll_id, moderator_id, question, started_at, expired_at, choice);

		return pollrepo.save(poll_object);

	}
	
	@JsonView(Display.WithoutResult.class)
	@RequestMapping(value="/polls/{polls_id}", method=RequestMethod.GET)
	public Polls pollViewNR(@PathVariable("polls_id") String id){

		Polls pollObj = null;

		if(!id.equals("")){
			if(pollrepo!=null && pollrepo.count()>0){
				 pollObj = pollrepo.findOne(id);
			}
		}
		return pollObj;
	}
	
	@RequestMapping(value="/moderators/{mod_id}/polls/{poll_id}",method=RequestMethod.GET)
	public @ResponseBody Polls pollViewWR(@PathVariable("mod_id") String mod_id, @PathVariable("poll_id") String poll_id) throws Exception{
		Polls obj = null;
		if(!mod_id.equals("") && !poll_id.equals("")){
			if(pollrepo!=null && pollrepo.count()>0){
				Polls pollObj = pollrepo.findOne(poll_id);
				if(pollObj.getModerator_id().equals(mod_id)){
					obj = pollObj;
				}
			}
		}
		return obj;
	}
	

	@RequestMapping(value="/moderators/{mod_id}/polls",method=RequestMethod.GET)
	public @ResponseBody ArrayList<Polls> listPolls(@PathVariable("mod_id") String mod_id) throws Exception{
		ArrayList<Polls> mod_pollList = new ArrayList<Polls>();
		if(!mod_id.equals("")){
			if(pollrepo.count()>0 && pollrepo!=null){
				List poll_list = pollrepo.findAll();

					for(int i=0;i< poll_list.size();i++){
						Polls obj = (Polls)poll_list.get(i);
						if(obj.getModerator_id().equals(mod_id)){
							mod_pollList.add(obj);
					}
				}
			}
		}
		return mod_pollList;
	}

	@RequestMapping(value="/moderators/{mod_id}/polls/{poll_id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> delete_polls(@PathVariable String mod_id,@PathVariable String poll_id) {

		boolean flag = false;
		if(!mod_id.equals("") && !poll_id.equals("")){
			if(pollrepo.count()>0 && pollrepo!=null){
				Polls pollObj = pollrepo.findOne(poll_id);
				if(pollObj.getModerator_id().equals(mod_id)){
					pollrepo.delete(poll_id);
					flag = true;
				}
			}
		}

		if (flag)
			return new ResponseEntity<String>("Poll Removed", HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/polls/{poll_id}", method=RequestMethod.PUT)
	public ResponseEntity<String> votePoll(@PathVariable String poll_id, @RequestParam int choice){
		boolean flag = false;

		List poll_list = pollrepo.findAll();
		Polls update = pollrepo.findOne(poll_id);

		if(!poll_id.equals("")){
			if(poll_list!=null && update!= null){
				for(int count=0;count<poll_list.size();count++){
					Polls obj = (Polls)poll_list.get(count);
					if(obj.getId().equals(poll_id)){
						obj.getResults()[choice]++;
						pollrepo.save(obj);
						flag = true;
						break;
					}

				}
			}
		}

		if(flag)
			return new ResponseEntity<String>("Vote Captured",HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<String>("Poll Not Available",HttpStatus.NO_CONTENT);

	}

}
