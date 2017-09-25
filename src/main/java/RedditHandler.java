import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class RedditHandler {

    static void recoverImages(String sub) throws IOException {
        RestClient client = new HttpRestClient();
        client.setUserAgent("ZotBot/1.0");
        User user = new User(client,Main.redditUsername, Main.redditPass);
        Submissions submissions = new Submissions(client, user);
        ArrayList<Submission> subreddit = new ArrayList<>(submissions.ofSubreddit(sub, SubmissionSort.HOT, -1,300, null,null,true));
        for(Submission x : subreddit ) {
            if(!x.isSelf() && x.getScore() > 1000 && !x.isNSFW()) {
                //System.out.println(x.getPermalink());
                File imageFile = new File(System.getProperty("user.dir") + "/memes/" + x.getPermalink().substring(sub.length() + 13,sub.length() + 19) + ".png");
                if(!imageFile.exists()) {
                    URL url = null;
                    try {
                        url = new URL(x.getURL());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    assert url != null;
                    if(url.toString().endsWith("png") || url.toString().endsWith("jpg") || url.toString().startsWith("https://imgur.com")) {
                        try {
                            BufferedImage img = ImageIO.read(url);
                            if (img == null) {
                                //noinspection UnusedAssignment
                                img = ImageIO.read(new URL("https://i.imgur.com/" + url.toString().substring(18)));
                            } else
                                ImageIO.write(img, "png", imageFile);
                        } catch (IIOException e) {
                            System.out.println(url);
                        }
                    }
                }
            }
        }
    }
}