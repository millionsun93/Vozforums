package com.quanlt.vozforums.data;

import com.quanlt.vozforums.data.model.VozBase;
import com.quanlt.vozforums.data.model.VozForum;
import com.quanlt.vozforums.data.model.VozPost;
import com.quanlt.vozforums.data.model.VozThread;
import com.quanlt.vozforums.data.model.VozUser;
import com.quanlt.vozforums.data.remote.VozRemoteDataSource;
import com.quanlt.vozforums.utils.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by quanlt on 01/01/2017.
 */

@Singleton
public class VozDataRepositoryImpl implements VozDataRepository {
    private final VozRemoteDataSource mRemoteDataSource;

    @Inject
    public VozDataRepositoryImpl(VozRemoteDataSource mRemoteDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
    }

    @Override
    public Observable<VozForum> getForum(String path) {
        return mRemoteDataSource.get(path)
                .map(data -> convertForums(data));
    }

    @Override
    public Observable<VozThread> getThread(String path) {
        return mRemoteDataSource.get(path)
                .map(data -> convertThread(data));
    }

    private VozThread convertThread(String data) {
        Document doc = Jsoup.parse(data, Constants.BASE_URL);
        VozThread thread = new VozThread();
        thread.setTitle(doc.title());
        Elements elements = doc.select("table[id^=post]");
        for (Element element : elements) {
            VozPost post = new VozPost();
            VozUser user = new VozUser();
            post.setHref(element.select("a[href^=showpost]").attr("href"));
            post.setDate(element.select("a[name^=post]").text());
            if (post.getDate().equals("")) {
                post.setDate(element.select("a[name^=post]").first().parent().text());
            }
            user.setUsername(element.select("a.bigusername").text());
            user.setHref(element.select("a.bigusername").attr("href"));
            user.setOnline(
                    element.select("img.inlineimg[src^=images/statusicon/user]").attr("src").contains("online"));
            String userTitle = element.select("td>div.smallfont").get(0).text();
            if (userTitle.contains("deleted")) {
                post.setDeleted(true);
            } else {
                user.setTitle(userTitle);
            }
            String joinDate = element.select("div.smallfont>div:contains(Join Date)").text();
            if (joinDate != null && !joinDate.equals("")) {
                user.setJoinDate(joinDate.substring(joinDate.indexOf("Join Date: ") + 11));
            }
            String postCount = element.select("div.smallfont>div:contains(Posts:)").text();
            if (joinDate != null && !joinDate.equals("")) {
                user.setPosts(postCount);
            }
            String location = element.select("div.smallfont>div:contains(Location:)").text();
            if (location != null && !location.equals("")) {
                user.setLocation(location);
            }
            if (element.select("a[href^=member.php]") != null) {
                user.setHref(element.select("a[hr=member.php]").attr("href"));
                user.setUsername(element.select("a[href^=member.php]").text());
                if (element.select("a[href^=member.php] > img") != null)
                    user.setAvatar(Constants.BASE_URL + element.select("a[href^=member.php] > img").attr("src"));
            }

            post.setAuthor(user);
            if (element.select("div[id^=post_message]") != null) {
                post.setContent(element.select("div[id^=post_message]").html());
            }
            thread.getPosts().add(post);
        }

        Elements pageElements = doc.select("div.pagenav > table > tbody > tr > td[class~=(alt1|alt2)]");
        for (int i = 0; i < pageElements.size() / 2; i++) {
            if (pageElements.get(i).className().equals("alt1")) {
                thread.getPages().add(new VozBase(pageElements.get(i).child(0).text(), pageElements.get(i).child(0).attr("href")));
            } else {
                thread.getPages().add(new VozBase(pageElements.get(i).child(0).text()));
            }
        }
        return thread;
    }

    private VozForum convertForums(String source) {
        VozForum forum = new VozForum();
        Document doc = Jsoup.parse(source, Constants.BASE_URL);
        forum.setTitle(doc.title());
        // sub forum
        Elements subForumNode = doc.select("td[id~=f+[0-9]]");
        for (Element element : subForumNode) {
            VozForum subForum = new VozForum();
            subForum.setHref(element.select("a").attr("href"));
            subForum.setTitle(element.select("a").text());
            Element lastPostElement = element.nextElementSibling();
            VozUser author = new VozUser();
            author.setHref(lastPostElement.select("a[href^=member.php]").attr("href"));
            author.setUsername(lastPostElement.select("a[href^=member.php]").text());
            forum.getForums().add(subForum);
        }
        Elements threadList = doc.select("tbody[id^=threadbits_forum] > tr");
        for (Element element : threadList) {
            VozThread thread = new VozThread();
            thread.setTitle(element.select("a[id^=thread_title]").text());
            thread.setContent(element.select("td[id^=td_threadtitle]").attr("title"));
            thread.setHref(element.select("a[id^=thread_title]").attr("href"));
            thread.setSticky(element.select("a[id^=thread_title]").hasClass("vozsticky"));
            VozUser user = new VozUser();
            user.setUsername(element.select("td[id^=td_threadtitle] > div.smallfont").text());
            String userUrl = element.select("td[id^=td_threadtitle] > div.smallfont > span").attr("onclick");
            Pattern pattern = Pattern.compile("'(.*?)'");
            Matcher matcher = pattern.matcher(userUrl);
            if (matcher.find()) {
                user.setHref(matcher.group(1));
            }
            thread.setAuthor(user);
            if (element.select("td:last-child").text().contains("Thread deleted")) {
                continue;
            }
            thread.setViewsCount(shortenLargeNumber(element.select("td:last-child").text()));
            thread.setRepliesCount(shortenLargeNumber(element.select("td:last-child").get(0).previousElementSibling().text()));
            VozPost lastPost = new VozPost();
            Elements lastPostElement = element.select("td > div:has(span.time)");
            lastPost.setDate(lastPostElement.text().substring(0, lastPostElement.text().indexOf("by")));
            VozUser lastPostAuthor = new VozUser();
            lastPostAuthor.setUsername(lastPostElement.select("a[href^=member.php]").text());
            lastPostAuthor.setUsername(lastPostElement.select("a[href^=member.php]").attr("href"));
            lastPost.setHref(lastPostElement.select("a[href^=showthread]").attr("href"));
            lastPost.setAuthor(lastPostAuthor);
            thread.setLastPost(lastPost);
            String rate = element.select("img.inlineimg[src^=images/rating]").attr("src");
            pattern = Pattern.compile("(\\d+)");
            if (rate != null && !rate.equals("")) {
                matcher = pattern.matcher(rate);
                if (matcher.find()) {
                    thread.setRateCount(Integer.parseInt(matcher.group(1)));
                }
            }
            forum.getThreads().add(thread);
        }
        Elements pageElements = doc.select("div.pagenav > table > tbody > tr > td[class~=(alt1|alt2)]");
        for (int i = 0; i < pageElements.size() / 2; i++) {
            if (pageElements.get(i).className().equals("alt1")) {
                forum.getPages().add(new VozBase(pageElements.get(i).child(0).text(), pageElements.get(i).child(0).attr("href")));
            } else {
                forum.getPages().add(new VozBase(pageElements.get(i).child(0).text()));
            }
        }
        return forum;
    }

    private  String shortenLargeNumber(String num) {
        String t = num.replaceAll("\\D", "");
        int res = Integer.parseInt(t);
        if (res > 1000000000) {
            return res / 1000000000 + "B";
        } else if (res > 1000000) {
            return res / 1000000 + "M";
        } else if (res > 1000) {
            return res / 1000 + "K";
        }
        return res + "";
    }
}
