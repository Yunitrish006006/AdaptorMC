package net.yunitrish.adaptor.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.yunitrish.adaptor.Adaptor;

import java.util.Optional;
import java.util.function.Consumer;

public class DiscordBot extends ListenerAdapter {

    private Optional<JDA> connection = Optional.empty();

    public void connect(final String token) throws InterruptedException, InvalidTokenException {
        final JDABuilder builder = JDABuilder.createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(this);
        connection = Optional.of(builder.build());
        connection.get().awaitReady();
        Adaptor.LOGGER.info("Bot Connected");
    }

    public void disconnect() {
        withConnection(c -> {
            c.shutdownNow();
            Adaptor.LOGGER.info("Disconnected");
        });
        connection = Optional.empty();
    }

    public boolean withConnection(Consumer<JDA> action) {
        if (connection.isPresent()) {
            action.accept(connection.get());
            return false;
        }
        return false;
    }

    public boolean isConnected() {
        return connection.isPresent();
    }

    public void setStatus(String status) {
        withConnection(c -> c.getPresence().setActivity(Activity.playing(status)));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        DiscordChatCallback.EVENT.invoker().dispatch(event.getMessage());
    }
}
