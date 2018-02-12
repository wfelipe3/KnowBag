package knowbag.mail

import java.io.{File, FileInputStream, InputStreamReader}
import java.util
import java.util.Date
import java.util.concurrent.Executors
import javax.mail.{Folder, Message, Session}

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeFlow, GoogleClientSecrets}
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.gmail.{Gmail, GmailScopes}

import scala.collection.JavaConverters._
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by feliperojas on 2/8/18.
  */
object MailMain extends App {

  val props = System.getProperties
  props.setProperty("mail.store.protocol", "imaps")
  val session = Session.getDefaultInstance(props, null)
  val store = session.getStore("imaps")

  store.connect("imap.gmail.com", args(0), args(1))

  val inbox = store.getFolder("Inbox")
  inbox.open(Folder.READ_ONLY)


  def getMails(mails: Array[Message], inbox: Folder): Array[Message] = {
    inbox.search({ msg =>
      Option(msg.getSubject)
        .filter(s => s.toUpperCase().contains("BBVA"))
        .exists(_ => true)
    }, mails)
  }

  val count = inbox.getMessageCount

  private val pool = Executors.newFixedThreadPool(10)
  val executor = ExecutionContext.fromExecutor(pool)

  val fs = (0 to 99).map { i =>
    Future {
      val inbox = store.getFolder("Inbox")
      inbox.open(Folder.READ_ONLY)
      println(s"start execution for $i")
      val start = System.currentTimeMillis()
      val mails = getMails(inbox.getMessages(count - ((i + 1) * 100), count - (i * 100)), inbox)
        .map(message)
      println(s"finish execution for $i time ${System.currentTimeMillis() - start}")
      inbox.close(true)
      mails
    }(executor)
  }

  Await.result(Future.sequence(fs), Duration.Inf)
    .flatten
    .sortBy(_.date)
    .foreach(println)


  inbox.close(true)

  pool.shutdown()

  case class TestMessage(subject: String, content: String, date: Date)

  def message(m: Message) =
    TestMessage(m.getSubject, m.getContent.toString, m.getReceivedDate)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private def gmailTest = {
    val SCOPES = util.Arrays.asList(GmailScopes.GMAIL_LABELS)
    val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
    val DATA_STORE_DIR = new File(System.getProperty("user.home"), ".credentials/gmail-java-quickstart")
    val DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR)
    val JSON_FACTORY = JacksonFactory.getDefaultInstance

    def authorize() = {
      val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream("/Users/feliperojas/git/KnowBag/scala/mail-reader/client_id.json")))
      val flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(DATA_STORE_FACTORY)
        .setAccessType("offline")
        .build()

      val credentials = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user")
      println(s"Credentials saves to ${DATA_STORE_DIR.getAbsolutePath}")

      credentials
    }

    def getGmailService() = {
      val credential = authorize()
      new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
        .setApplicationName("gmail-reader")
        .build()
    }

    val gmail = getGmailService()
    val listResponse = gmail.users().labels().list("me").execute()
    val labels = listResponse.getLabels

    if (labels.size() == 0)
      println("No labels found")
    else {
      println("labels")
      labels.asScala.foreach(l => println(l.getName))
    }
  }


}
