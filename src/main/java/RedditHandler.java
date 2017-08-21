import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RedditHandler {

    public static void recoverImages(String sub) throws IOException {
        RestClient client = new HttpRestClient();
        client.setUserAgent("ZotBot/1.0");
        User user = new User(client,Main.redditUsername, Main.redditPass);
        try {
            user.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Submissions submissions = new Submissions(client, user);
        ArrayList<Submission> subreddit = new ArrayList<>(submissions.ofSubreddit(sub, SubmissionSort.HOT, -1,100, null,null,true));
        for(Submission x : subreddit ) {
            if(!x.isSelf() && x.getScore() > 1000) {
                System.out.println(x.getPermalink());
                if(! new File(System.getProperty("user.dir") + "/memes/" + x.getPermalink().substring(sub.length() + 11,sub.length() + 19) + ".png").exists()) {
                    URL url = null;
                    try {
                        url = new URL(x.getURL());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    BufferedImage img = ImageIO.read(url);
                    File imageFile = new File(System.getProperty("user.dir") + "/memes/" + x.getPermalink().substring(sub.length() + 11,sub.length() + 19) + ".png");
                    if(img == null) {
                        img = ImageIO.read(new URL("https://i.imgur.com/" + url.toString().substring(18)));
                    }
                    if(img != null)
                        ImageIO.write(img,"png",imageFile);
                }
            }
        }
    }
}