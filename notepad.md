## how to check server & client's mod version
(Main)ModInitializer:
```java
public static final String MOD_ID = "xxxxx";
public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
public static Identifier id(String name) {
	return Identifier.of(Main.MOD_ID, name);
}
public void onInitialize() {
	PayloadTypeRegistry.playC2S().register(VersionChecker.ID,VersionChecker.CODEC);
}
```
(Client)ClientModInitializer:
```java
public void onInitializeClient() {
	ClientPlayConnectionEvents.JOIN.register(Main.id("version_check"),(handler,sender,client)->{
		ClientPlayNetworking.send(new VersionChecker(Api.getModVersion()));
	});
}
```
(Server)DedicatedServerModInitializer:
```java
public void onInitializeServer() {
	ServerPlayNetworking.registerGlobalReceiver(
		VersionChecker.ID,
		(versionChecker,context)->{
			if (!versionChecker.modVersion().equals(Api.getModVersion())) {
				context
					.player()
					.networkHandler.disconnect(
						Text.translatable("<translation with 2 argument>",Main.MOD_ID,Api.getModVersion(),versionChecker.modVersion())
					);
			}
		}
	);
}
```
(Api)
```java
public record VersionChecker (String modVersion) implements CustomPayload {
	public static final CustomPayload.Id<VersionChecker> ID = new CustomPayload.Id<>(Main.id("version_check"));
	public static final PacketCodec<RegistryByteBuf, VersionChecker> CODEC = PacketCodec.tuple(
		PacketCodecs.STRING, VersionChecker::modVersion,
		VersionChecker::new
	);
	@Override
	public CustomPayload.Id<? extends CustomPayload> getId() {return ID;}

	public static String versionGet() {
		if (FabricLoader.getInstance().getModContainer(Main.MOD_ID).isPresent()){
			return FabricLoader.getInstance().getModContainer(Main.MOD_ID).get().getMetadata().getVersion().toString();
        	}
        	else{
			return "version not found";
		}
	}
}
```
