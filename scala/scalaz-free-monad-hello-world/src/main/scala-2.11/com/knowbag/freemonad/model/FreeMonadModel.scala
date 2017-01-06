package com.knowbag.freemonad.model

/**
  * Created by dev-williame on 1/4/17.
  */
object FreeMonadModel {

  type UserId = Int
  type UserName = String
  type UserPhoto = String

  type Tweets = List[String]

  final case class Tweet(userId: UserId, msg: String)
  final case class User(id: UserId, name: UserName, photo: UserPhoto)

  sealed trait Service[A]
  final case class GetTweets(userId: UserId) extends Service[List[Tweet]]
  final case class GetUserName(userId: UserId) extends Service[String]
  final case class GetUserPhoto(userId: UserId) extends Service[String]
  final case class GetUserId(userId: UserId) extends Service[String]

  final case class Request[A](service: Service[A])
  case class Response[A](res: A)

}
