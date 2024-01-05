package tedwon.magpie;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import java.net.URI;

public class JiraClientExample {

    public static void main(String[] args) {
        // Replace these values with your Jira server URL and access token
        String jiraServerUrl = "";
        String accessToken = "";

        try {
            // Create a JiraRestClient using access token
            JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            URI serverUri = new URI(jiraServerUrl);
            JiraRestClient restClient = factory.create(serverUri, new BearerHttpAuthenticationHandler(accessToken));

            // Replace "ISSUE-123" with the key of the issue you want to retrieve
            Issue issue = restClient.getIssueClient().getIssue("ISSUE-5384").claim();

            // Print some details of the retrieved issue
            System.out.println("Issue Key: " + issue.getKey());
            System.out.println("Summary: " + issue.getSummary());

            final String jql = "assignee = currentUser() AND status not in (Closed, Done) ORDER BY due ASC";
            SearchResult searchResult = restClient.getSearchClient().searchJql(jql).claim();
            Iterable<Issue> issues = searchResult.getIssues();
            issues.forEach(issue1 -> System.out.println("Summary: " + issue1.getSummary()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}