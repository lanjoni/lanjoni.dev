# How to write a CRUD CLI using Elixir and ScyllaDB

If you know high traffic applications for communication, applications that need low latency and good fault tolerance, you have most likely already come across the names Elixir (as a programming language) and ScyllaDB (being a database NoSQL aimed at low latency). The objective of both is very similar: to work with applications that generally require greater care for stability.

ScyllaDB is recognized worldwide as an extremely fast database, being based on Apache Cassandra, bringing several improvements for low latency. Furthermore, ScyllaDB is completely free, open source, and is distributed under the GNU AGPL license.

Elixir, on the other hand, is a programming language known for dealing very well with the applicability of concepts such as concurrency and fault tolerance and this is thanks to the Erlang ecosystem, which in this case, Elixir makes use of the virtual machine called BEAM, designed specifically to work with high-volume messaging applications.

The purpose of this article is to offer an introduction to how you can create your first application using both, opening doors and possibilities for your future with development with these two incredible technologies.

## Table of Contents
- [Starting the project](#starting-the-project)
  - [Installing Elixir](#installing-elixir)
  - [Installing ScyllaDB](#installing-scylladb)
  - [Starting our project](#starting-our-project)
- [Configuring the project](#configuring-the-project)
  - [Defining dependencies](#defining-dependencies)
  - [Configuring .env](#configuring-env)
  - [Defining our connection module](#defining-our-connection-module)
- [Implementing actions with the database](#implementing-actions-with-the-database)
- [Commands](#commands)
  - [Add](#add)
  - [List](#list)
  - [Delete](#delete)
  - [Stress](#stress)
- [Implementing user interaction](#implementing-user-interaction)
- [Running our application](#running-our-application)
- [Conclusion](#conclusion)

## Starting the project

Beforehand we need to install both Elixir and ScyllaDB. I won't go into too much detail about the installation because this makes the article simpler. So let's start with installing Elixir.

### Installing Elixir

To install Elixir in general, there are two main methods: installing directly from your package manager or using a version manager for programming languages. In my case I will be using `asdf` as responsible for managing my Elixir versions. If you want to install Elixir using your package manager, click [here](https://elixir-lang.org/install.html) to receive more information.

To install `asdf` you can check by clicking [here](https://asdf-vm.com/guide/getting-started.html). As a preference, I always choose to install with "Bash & Git" or "Zsh & Git". Once installed, we will prepare the installation to receive the latest version of Erlang and Elixir in our project. To install Erlang:

```bash
$ asdf plugin add erlang https://github.com/asdf-vm/asdf-erlang.git

$ asdf install erlang latest

$ asdf global erlang latest
```

> Did you have any problems with the installation? Access the [official guide](https://github.com/asdf-vm/asdf-erlang).

To install Elixir:

```bash
$ asdf plugin-add elixir https://github.com/asdf-vm/asdf-elixir.git

$ asdf install elixir latest

$ asdf global elixir latest
```

> Did you have any problems with the installation? Access the [official guide](https://github.com/asdf-vm/asdf-elixir).

Well, now that we have both installed we can test to see if everything is ok by typing `elixir -v` in our terminal emulator and we will get a response similar to:

```
Erlang/OTP 25 [erts-13.2.2.2] [source] [64-bit]

Elixir 1.13.4 (compiled with Erlang/OTP 23)
```

> These are my installed versions for Erlang and Elixir. If you want to install the same versions as me on your machine, just modify the `asdf install` command, replacing `latest` with the version number and then set it to `asdf global elixir 1.13.4` in this case.

### Installing ScyllaDB

There are several ways to use ScyllaDB, which you can use with [ScyllaDB Cloud](https://www.scylladb.com/product/scylla-cloud/), with ScyllaDB [installed on your machine](https://opensource.docs.scylladb.com/stable/getting-started/install-scylla/) or the way I will use it: with Docker containers.

If you don't already have Docker installed, I recommend accessing the [installation guide](https://docs.docker.com/engine/install/). If you want to use ScyllaDB Cloud or ScyllaDB installed on your machine, there's no problem, just be careful when placing the node links when initializing and configuring the project, but when we get to this part I'll explain it better.

Well, continuing... To run our container with ScyllaDB in Docker we will use the following command:

```bash
$ docker run --name some-scylla -p 9042:9042 -d scylladb/scylla
```

The `-p` option indicates that we want to bind port 9042 of the container with port 9042 of our machine, allowing our container to now be accessed directly on our `localhost:9042`.

For test the connection, after executing the command, wait a few seconds for everything to start correctly in the container and then type:

```bash
$ docker exec -it some-scylla cqlsh
```

So you will see a response similar to:

```
Connected to at 172.17.0.2:9042.
[cqlsh 5.0.1 | Cassandra 3.0.8 | CQL spec 3.3.1 | Native protocol v4]
Use HELP for help.
cqlsh> 
```

This is our console where we can execute commands to interact with ScyllaDB. By default, the language used is [CQL (Cassandra Query Language)](https://cassandra.apache.org/doc/latest/cassandra/cql/), very similar to the standard database SQL that you probably have already had contact.

Well, let's run a simple command to describe all the `keyspaces` we have in our container. `keyspaces` could be defined with a simple analogy: keyspace is basically the same as database when you use a relational database like MySQL or PostgreSQL for example (the definition goes _a little beyond that_, but I won't go into it very).

To describe your `keyspaces` run:

```cql
cqlsh> DESCRIBE KEYSPACES;
```

And you should see a response similar to:

```
system_schema system_traces system_distributed
```

We haven't created any `keyspace` yet, right? Well, let's create our keyspace, which in this case is for a `media_player`, with the command:

```cql
cqlsh> CREATE KEYSPACE media_player
     WITH replication = {'class': 'NetworkTopologyStrategy', 'replication_factor': '3'}
     AND durable_writes = true;
```

And let's create the table:

```cql
cqlsh> CREATE TABLE media_player.songs (
     id uuid,
     titletext,
     album text,
     artist text,
     created_at timestamp,
     PRIMARY KEY (id, created_at)
);
```

### Starting our project

For start a new project, run the command:

```bash
$ mix new media_player
```

> [Mix](https://elixir-lang.org/getting-started/mix-otp/introduction-to-mix.html) is much more than a dependency manager for Elixir, after all, with it we can run and manage our entire project. By default, Mix is already installed together with Elixir.

And a project will be created with the structure:

```
.
├── README.md
├── lib
│   └── media_player.ex
├── mix.exs
```

Well, now that we have our project initialized, we can start playing, so open your favorite code editor and let's go.

## Configuring the project

At this point we will configure our project in Elixir to install and use all the tools necessary to build our CLI, in addition to defining the first settings.

### Defining dependencies

After opening your code editor, notice that there is a file named `mix.exs`. This file is responsible for defining several attributes about our project, including the dependencies that will be used during its development.

Going down the page a little you will see an area that starts with `defp deps do`... Exactly in this part we will modify and insert the following dependencies:

```elixir
# Run "mix help deps" to learn about dependencies.
defp deps
  [
    {:dotenv, "~> 3.0"},
    {:decimal, "~> 1.0"},
    {:xandra, "~> 0.14"},
    {:elixir_uuid, "~> 1.2"}
  ]
end
```

- [Dotenv](https://github.com/avdi/dotenv_elixir): A port of dotenv to Elixir.
- [Decimal](https://github.com/ericmj/decimal): Arbitrary precision decimal arithmetic.
- [Xandra](https://github.com/lexhide/xandra): Fast, simple, and robust Cassandra/ScyllaDB driver for Elixir.
- [Elixir UUID](https://github.com/zyro/elixir-uuid): UUID generator and utilities for Elixir. See RFC 4122.

Well, now that we have our dependencies defined we can run it in our terminal emulator:

```bash
$ mix deps.get
```

> The command above will install all dependencies that were defined in our `mix.exs`.

Great! Now we can configure our environment variables for the project within `.env`.

### Configuring .env

Well, let's now create a file called `.env` at the root of our project (that's right, at the same level as our `mix.exs`). It will be responsible for defining the first configurations of our project, including the environment variables that will be used to connect our cluster.

When creating the file and opening it in the code editor, we will define:

```
SCYLLADB_USERNAME=
SCYLLADB_PASSWORD=
SCYLLADB_NODE=
SCYLLADB_KEYSPACE=
SCYLLADB_TABLE=
```

- `SCYLLADB_USERNAME`: username configured to connect to ScyllaDB.
- `SCYLLADB_PASSWORD`: password configured for the user.
- `SCYLLADB_NODE`: complete url to connect to our node, you can enter just one url (such as `localhost:9042` for example) or define the complete nodes generated separated by a comma (such as `scylla-node1.com,scylla- node-2.com,scylla-node-3.com`).
- `SCYLLADB_KEYSPACE`: the keyspace generated for our application.
- `SCYLLADB_TABLE`: table that will be used for the respective keyspace.

This way our .env should look like:

```
SCYLLADB_USERNAME=scylla
SCYLLADB_PASSWORD=scylla
SCYLLADB_NODE=localhost:9042
SCYLLADB_KEYSPACE=media_player
SCYLLADB_TABLE=songs
```

Perfect! Now that our connection files are ready, we can start structuring the project, shall we?

### Defining our connection module

It is elegant and interesting to separate the connection module in a separate area of our project, allowing for more practical maintenance and attractive organization, so let's create two directories with the path `lib/media_player/config`. This directory will be responsible for storing two main configuration files: the file for connecting to the Cluster and the file for defining the keyspace and table.

Well, let's create two files inside the `lib/media_player/config` directory, called `connection.ex` and `database.ex`. This way our directory structure will now be:

```
.
├── README.md
├── lib
│   ├── media_player
│   │   └── config
│   │   ├── connection.ex
│   │   └── database.ex
│   └── media_player.ex
├── mix.exs
```

> Yes, I omitted the `deps` directory because it contains the dependencies, that is, nothing that we are going to modify manually, don't worry, in addition to omitting the `test` directory because it will contain the tests that can be implemented, but, no let's implement it for now.

Well, now we can define the connection to our cluster, starting with the `database.ex` file.

```elixir
defmodule MediaPlayer.Config.Database do
  import Dotenv

  load()

  def start_link do
    options = [
      username: System.get_env("SCYLLADB_USERNAME"),
      password: System.get_env("SCYLLADB_PASSWORD")
    ]

    {:ok, cluster} =
      Xandra.Cluster.start_link(
        sync_connect: :infinity,
        authentication: {Xandra.Authenticator.Password, options},
        nodes:
          System.get_env("SCYLLADB_NODE")
          |> String.split(",")
      )

    cluster
  end
end
```

In this file we:

- We import the `Dotenv` library to manage the variables defined in our `.env` file;
- We load the variables with the `load()` function that comes from the `Dotenv` library;
- We created a function named `start_link` that will be responsible for starting the connection link with our cluster;
- In the function we define the `username` and `password` receiving these values from the `.env` file;
- We initialize the cluster with the function coming from `Xandra` with the name `Xandra.Cluster.start_link`, responsible for starting a connection link with the cluster
  - In this function we define that `sync_connect` is with value `:infinity`! This means that it will try to make the connection with an infinite expected response time (that is, the module will wait as long as necessary to make the complete connection of all nodes). To read more about click [here](https://hexdocs.pm/xandra/Xandra.Cluster.html#start_link/1);
  - We define that we are going to perform authentication and pass `options` (which was defined previously) as a parameter;
  - We define our nodes by loading from our `.env` file and performing a division with the commas found, distributing them in a list (`nodes` expects a list of urls to make the connection, which is why the split is necessary to create this list) . If you use ScyllaDB Cloud, this is what will make everything work perfectly;
- We return the cluster with the connection ready.

Perfect! Our connection file is ready. Now let's configure a simple area that will only be responsible for returning the keyspace and the table, called `connection.ex`:

```elixir
defmodule MediaPlayer.Config.Connection do
  import Dotenv

  load()

  def keyspace() do
    System.get_env("SCYLLADB_KEYSPACE")
  end

  def table() do
    System.get_env("SCYLLADB_TABLE")
  end
end
```

Basically the only functionality of this module is to have two functions to return the keyspace and the table that we will be using, without the need to always use the `.env` library!

## Implementing actions with the database

Well, now is another important point: as our project will have commands, therefore, it would be interesting to create a specific module to handle these commands, right? Perfect! However, before that, what do you think about creating a module to execute queries in the database, so we can centralize where the queries will be executed.

Well, this is the time to create a file in `lib/media_player` with the name `actions.ex`. So our directory structure will look like this:

```
.
├── README.md
├── lib
│   ├── media_player
│   │   ├── actions.ex
│   │   └── config
│   │   ├── connection.ex
│   │   └── database.ex
│   └── media_player.ex
├── mix.exs
```

Wonder! With the file created we can now create two specific functions, but why two? Simple: the `Xandra.Cluster.execute` function has two variations, the first with two parameters (the cluster and the query to be executed) and the second with three parameters (the cluster, the query to be executed and the parameters, being a list that is mainly used to prepare our query).

Let's go then, our module should look like this:

```elixir
defmodule MediaPlayer.Actions do
  def cluster, do: MediaPlayer.Config.Database.start_link()

  def run_query(query) do
    case Xandra.Cluster.execute(cluster(), query) do
      {:ok, result} ->
        result

      {:error, error} ->
        IO.inspect(error)
    end
  end

  def run_query(query, params) do
    prepared = Xandra.Cluster.prepare!(cluster(), query)

    case Xandra.Cluster.execute(cluster(), prepared, params) do
      {:ok, result} ->
        result

      {:error, error} ->
        IO.inspect(error)
    end
  end
end
```

In this file we:

- We define a local `cluster` function that does nothing more than initialize and return the connection link to the cluster;
- We define a function `run_query/1` that takes just one parameter (being just the query, after all, the cluster is already a local function and we know that we will always execute actions on it);
  - We tried to perform the query with the function `Xandra.Cluster.execute`;
    - If the return is `:ok`, it means that everything went well, then we return the complete result (a complete map of the query);
    - If the return is `:error` it means that an error occurred when performing the query, then we inspect the specific error;

> An important detail is about the assignment made with `{:ok, result}` and `{:error, error}`, since in Elixir everything has a return, always being a map starting with an atom validating the return type that was given (I recommend seeing more about [atoms](https://elixir-lang.org/getting-started/basic-types.html#atoms)), so we bind values with a map!

- We define a function `run_query/2` that takes two parameters (only the query and the parameters to be executed):
  - We tried to perform the query with the `Xandra.Cluster.execute` function:
    - If the return is `:ok`, it means that everything went well, then we return the complete result (a complete map of the query);
    - If the return is `:error` it means that an error occurred when performing the query, then we inspect the specific error;

If you want to see more about how the `Xandra.Cluster.execute` function works, click [here](https://hexdocs.pm/xandra/Xandra.Cluster.html#execute/3).

A detail that you may not have noticed: both functions have the same name, however, they differ in terms of the number of parameters! This makes the magic happen with Elixir. Functions are defined along with the number of parameters they expect to receive, so I put the name of the function followed by a slash "/" with the number of parameters. If you want to know more about it, click [here](https://elixirschool.com/en/lessons/basics/functions/#functions-and-pattern-matching-4) to better understand how Elixir works with Pattern Matching.

## Commands

Well, now is the long-awaited moment: adding the functions responsible for executing commands in our application! To do this, we will create a file in `lib/media_player` with the name `commands.ex`. This way our directory structure will be equal to:

```
.
├── README.md
├── lib
│   ├── media_player
│   │   ├── actions.ex
│   │   ├── commands.ex
│   │   └── config
│   │   ├── connection.ex
│   │   └── database.ex
│   └── media_player.ex
├── mix.exs
```

Let's start by creating the basis of our entire module:

```elixir
defmodule MediaPlayer.Commands do
  alias MediaPlayer.Actions, as: Actions
  alias MediaPlayer.Config.Connection, as: Connection

  defp keyspace, do: Connection.keyspace()
  defp table, do: Connection.table()
end
```

Basically above we defined that we will have:

- The alias `Actions` to refer to the `MediaPlayer.Actions` module;
- The alias `Connection` to refer to the `MediaPlayer.Config.Connection` module;
- The private function `keyspace` that returns the keyspace value that we will use;
- The private function `table` that returns the table value that we will use;

Well, now we can start implementing the commands, shall we?

### Add

Well, this command will be used to add songs to our database. Therefore, we will separate it into two main functions, namely `add` and `add_from`, which receive four parameters to be executed. The `add` function will only collect the data to be inserted:

```elixir
defmodule MediaPlayer.Commands do
  alias MediaPlayer.Actions, as: Actions
  alias MediaPlayer.Config.Connection, as: Connection

  defp keyspace, do: Connection.keyspace()
  defp table, do: Connection.table()

  def add_from(title, album, artist, created) do
    query =
      "INSERT INTO #{keyspace()}.#{table()} (id, title, album, artist, created_at) VALUES (?, ?, ?, ?, ?);"

    {:ok, created, _} = DateTime.from_iso8601(created <> "T00:00:00Z")

    Actions.run_query(query, [UUID.uuid4(), title, album, artist, created])

    IO.puts("Song added!")
  end

  def add() do
    title = IO.gets("Enter the title of the song: ") |> String.trim()
    album = IO.gets("Enter the album of the song: ") |> String.trim()
    artist = IO.gets("Enter the artist of the song: ") |> String.trim()

    created =
      IO.gets("Enter the date the song was created (YYYY-MM-DD): ")
      |> String.trim()

    add_from(title, album, artist, created)
  end
end
```

Well, both functions are very specific, so let’s give an example of each one:

- The `add` function will:
  - Collect the song title;
  - Collect the music album;
  - Collect the song artist;
  - Collect the creation date of the song;
  - Invoke the `add_from` function passing the collected values as parameters;
- The `add_from/4` function will:
  - Receive four parameters;
  - Create the `query` that will be executed;
  - Convert the date format to ensure bind compatibility with Xandra;
  - Call the function to perform the query passing two parameters: the query and additional options in a list format (as explained previously)
  - Then just show the message on the screen when the song is inserted!

Another point: the "|>" pipe in Elixir works like the "|" pipe of Unix shells, being used to pass the return of a function as the first parameter of the next. Read more about pipes [here](https://elixirschool.com/en/lessons/basics/pipe_operator).

Well, now we have the function responsible for adding a created song! Let's go next time?

### List

Now let's create a function responsible for listing all the songs we have added. Therefore, we will have as a result:

```elixir
  def list
    query = "SELECT id, title, album, artist, created_at FROM #{keyspace()}.#{table()};"

    Actions.run_query(query)
    |> Enum.each(fn %{
                      "id" => id,
                      "title" => title,
                      "album" => album,
                      "artist" => artist,
                      "created_at" => created_at
                    } ->
      IO.puts(
        "ID: #{id} | Title: #{title} | Album: #{album} | Artist: #{artist} | Created At: #{created_at}"
      )
    end)
  end
```

The `list` function does not receive any parameters, after all, it will print the songs that were added to the screen, like this:

- We define a query by selecting `id, title, album, artist, created_at` and to define the `keyspace` and `table` remember that we already have two functions that return these values!
- We tried to perform a simple query (which has no additional options besides `query`) and its return we passed to an `Enum.each` (similar to a `foreach` in other programming languages);
  - Within each we pass an anonymous function responsible for handling the return, which in this case expects to receive a map with the respective fields that were selected;
  - We print specific values;
- In the end this will be our return;

An important detail: in this case, `run_query` would return more than one value if we had multiple rows inserted, right? Well, `run_query` returns a complete list of maps with their respective values, so we use `Enum.each` to treat each index in the list we have.

### Delete

Well, the `delete` command is a little more complex because we need the user to type the index he wants to delete, validating it against the list of indexes that were returned! Therefore, the implemented function would result in something like:

```elixir
  def delete() do
    query = "SELECT id, title, album, artist, created_at FROM #{keyspace()}.#{table()};"

    songs =
      Actions.run_query(query)
      |> Enum.with_index(fn %{
                              "id" => id,
                              "title" => title,
                              "album" => album,
                              "artist" => artist,
                              "created_at" => created_at
                            },
                            index ->
        IO.puts(
          "Index: #{index + 1} | Title: #{title} | Album: #{album} | Artist: #{artist} | Created At: #{created_at}"
        )

        %{id: id, title: title, album: album, artist: artist, created_at: created_at}
      end)

    {input, _} = IO.gets("Enter the index of the song you want to delete: ") |> Integer.parse()

    case Enum.at(songs, input - 1) do
      %{} = song ->
        query = "DELETE FROM #{keyspace()}.#{table()} WHERE id = ? AND created_at = ?;"

        Actions.run_query(query, [song.id, song.created_at])

        IO.puts("Song deleted!")

      nil ->
        IO.puts("Invalid index.")
    end
  end
```

Well, basically we have in this function:

- Definition of the `query` returning all added songs;
- Listing similar to the previously implemented `list` function;
  - We try to perform the query in `Actions.run_query` and pass it to `Enum.with_index` to have the indexes;
  - This time instead of presenting the `id` of the song we present a manual index (instead of starting with 0 it starts with 1, hence `index + 1`), in which the user will type (more practical to type a number integer than a UUID isn't it?);
  - We print the values;
  - We added the complete list in `songs`;
- We wait for the user to make an entry stating which index he wants to delete and we parse the entry that will be saved in `input`;
- We check whether the index entered is present in `songs`;
  - If you are:
    - We create the query;
    - We tried to perform the query in `Actions.run_query` and added the list of options to bind to the query as a parameter;
    - We print the message informing that the song has been deleted;
  - If not:
    - We print that the index is invalid

This way we finish our function to delete a song!

### Stress

Great! The commands that necessarily manipulate user input are finished! Now let's create an additional command that will be responsible for performing a stress test on our database. We will define two functions, namely the `stress` function that will initialize the command, in addition to a private function with the name `generate_stress_query` that receives a parameter with the name `some_id`, with the index being inserted.

First of all, we must add the following section at the beginning of our module:

```elixir
defmodule MediaPlayer.Commands do
  use Task
  ...
```

With `Task` we can perform asynchronous calls with better practicality and performance. To read more click [here](https://hexdocs.pm/elixir/1.12/Task.html).

Thus, implementing our functions:

```elixir
  defp generate_stress_query(some_id) do
    current_date = Date.to_string(Date.utc_today())

    "INSERT INTO #{keyspace()}.#{table()} (
      id, title, album, artist, created_at
    ) VALUES (
      #{UUID.uuid4()},
      'Test Song #{some_id}',
      'Test Artist #{some_id}',
      'Test Album #{some_id}',
      '#{current_date}'
    );"
  end

  def stress
    start = Time.utc_now()
    cluster = MediaPlayer.Config.Database.start_link()

    # Simple stress test
    1..100_000
    |> Task.async_stream(
      fn id ->
        IO.puts("[#{id}] Adding seed")
        Xandra.Cluster.execute(cluster, generate_stress_query(id))
      end,
      max_concurrency: 500
    )
    |> Enum.to_list()

    IO.puts("Time taken: #{Time.diff(Time.utc_now(), start, :second)} seconds")
  end
```

Basically we have:

- `stress` function:
  - Mark the start time of the function with `Time.utc_now()`;
  - Initializes the cluster manually for better performance and handling.
  - We initiate a call from 1 to 100000 that goes asynchronously:
    - Define an anonymous function that receives an `id`;
    - We print that we are adding a certain index;
    - We tried to perform the query with `Xandra.Cluster.execute` by calling the `generate_stress_query` function, responsible for generating a complete query with the `id` provided;
    - We set a `max_concurrency` of 500 to limit the number of asynchronous calls;
  - We format it into a list;
  - We print the time spent to carry out the entire test, calculating the difference between the current time and the start time in seconds;
- `generate_stress_query` function:
  - Receives an `id` as a parameter to generate the query;
  - Sets the current date to insert;
  - Returns the complete query already formatted;

Well, that way our functions for the stress test are ready! Now we must implement the command inputs that a user can enter!

## Implementing user interaction

Let's modify our main module, `MediaPlayer` in the `media_player.ex` file! Well, first let's define an alias for the commands:

```elixir
defmodule MediaPlayer
     alias MediaPlayer.Commands, as: Commands
end
```

The alias will be used to refer to the `MediaPlayer.Commands` module!

The first function we will implement is `loop`, which will be responsible for directing the commands, receiving user input and remaining in an infinite loop always waiting for input, see:

```elixir
  def loop do
    IO.puts("-------------------------------------")
    IO.puts("Type any command: ")
    command = IO.gets("") |> String.trim()

    case command do
      "!add" ->
        Commands.add()
        loop()

      "!list" ->
        Commands.list()
        loop()

      "!delete" ->
        Commands.delete()
        loop()

      "!stress" ->
        Commands.stress()
        loop()

      "exit" ->
        IO.puts("Bye bye!")
        :OK

      _ ->
        IO.puts("Command not found!")
        loop()
    end
  end
```

Basically we wait for user input with a command and enter a `case`:

- `!add` invokes the `Commands.add()` function;
- `!list` invokes the `Commands.list()` function;
- `!delete` invokes the `Commands.delete()` function;
- `!stress` invokes the `Commands.stress()` function;
- `exit` prints a goodbye message and returns `:ok`, defining that the function no longer enters a recursive loop, ending our application;
- `_` prints a command not found message and enters a recursive loop, waiting for new input;

Well, now we have our main function ready! But how are we going to execute it? Simple, let's create a `start` function that will be responsible for starting our application, in addition to a `run` function, which will be the function invoked by the initial `start` function, see:

```elixir
  def start(_, _) do
    run()
    {:ok, self()}
  end

  def run do
    IO.puts("-------------------------------------")
    IO.puts("- ScyllaDB Cloud Elixir Media Player -")
    IO.puts("- Leave a star on the repo -")
    IO.puts("-------------------------------------")
    IO.puts("Here some possibilities")
    IO.puts(" !add - add new song")
    IO.puts(" !list - list all songs")
    IO.puts(" !delete - delete a specific song")
    IO.puts(" !stress - stress testing with mocked data")
    IO.puts("-------------------------------------")

    loop()
  end
```

Basically we have:

- `start` function:
  - It receives two parameters, but we will not use them, so we define them as `_`;
  - Invokes the `run` function;
  - Returns `{:ok, self()}` to define that the function was executed successfully, closing our application;
- `run` function:
  - Print a welcome message;
  - Prints a message with command possibilities;
  - Invokes the `loop` function to start the infinite loop;

This concludes our complete application! Now are we going to execute it?

## Running our application

Well, now that we have our application ready, let's execute the command:

```bash
$ mix run
```

This way we can start interacting with the application!

See a demo of the project:

[![asciicast](https://asciinema.org/a/608562.svg)](https://asciinema.org/a/608562)

## Conclusion

Thank you very much if you read this far! The purpose of this article was to demonstrate how we can use ScyllaDB with Elixir, making a simple demonstration using Xandra as responsible for the connection between the two. I highly recommend following content from [ScyllaDB University](https://university.scylladb.com/) for more quality educational content, as well as seeing articles about using ScyllaDB in general with [DanielHe4rt](https://dev.to/danielhe4rt) and [Cherry Ramatis](https://dev.to/cherryramatis)! Both are always contributing and publishing magnificent content.

I also recommend getting to know the project [Getting Started with ScyllaDB Cloud](https://github.com/scylladb/scylla-cloud-getting-started) which demonstrates the use of ScyllaDB in general, but, delving a little deeper into the use of ScyllaDB's own platform for managing your Cloud clusters, cool isn't it?

In the same repository you can access the project using Elixir too, so if you want to see the complete source code, know that it is at the same link mentioned above!

I hope you enjoyed the content and I hope it clarified the use of ScyllaDB with Elixir. I also hope that you are even more interested in receiving more content like this or learning even more about Elixir. Thank you very much, and see you next time!
