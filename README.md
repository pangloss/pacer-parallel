# Pacer::Parallel

Parallelize [Pacer](https://github.com/pangloss/pacer) Routes.

## Usage

The following will create a route that will do the operation in the block in 2 parallel threads, while consuming the source data and
enqueueing it for consumption by those threads in one additional thread:

```ruby
  g.v.parallel { |v| v.some_expensive_operation }
```

There are a few simple options, all optional. By default in_buffer is equal to the number of threads, and output is not buffered.

```ruby
  g.v.parallel(threads: 8, in_buffer: 4, out_buffer: 10) do |v|
    v.all(&:out)
  end
```

## Installation

Add this line to your application's Gemfile:

    gem 'pacer-parallel'

And then execute:

    $ bundle

Or install it yourself as:

    $ gem install pacer-parallel

## Contributing

1. Fork it
...
5. Create new Pull Request


## Notes


* eagerly consume (1) input and push into a channel
  * ChannelCapPipe
  * create a cap pipe that does this. The pipe's output is the channel
  * source data may be slow. Should probably not use a go block
  * 1 thread in a loop
* Control the construction of parallel pipes. Default 2 threads, make
it configurable.
  * standard copy split pipe can push the channel to subchannels
  * each parallel route pulls from the channel.
      * in a go block (waits will not block go thread pool)
      * ChannelReaderPipe
      * PathChannelReaderPipe
  * parallel routes are unmodified
  * cap each route - eagerly consume input and push into a channel
      * ChannelCapPipe again
  * ExhaustMergePipe + GatherPipe to create a route to an list of
    channels
  * use alts to read from any of the channels
      * ChannelFanInPipe



## Pipe structure built to create parallel route:

```
 CCP
 CSP (parallelism is 1 thread per pipe being split into)
   CRP -> Work ... -> CCP
   CRP -> Work ... -> CCP
   ...
 EMP
 GP
 CFIP
```
