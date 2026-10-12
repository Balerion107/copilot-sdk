/*---------------------------------------------------------------------------------------------
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *--------------------------------------------------------------------------------------------*/

// AUTO-GENERATED FILE - DO NOT EDIT
// Generated from: api.schema.json

package com.github.copilot.generated.rpc;

import com.github.copilot.CopilotExperimental;
import java.util.concurrent.CompletableFuture;
import javax.annotation.processing.Generated;

/**
 * API methods for the {@code user.settings} namespace.
 *
 * @since 1.0.0
 */
@javax.annotation.processing.Generated("copilot-sdk-codegen")
public final class ServerUserSettingsApi {

    private final RpcCaller caller;

    /** @param caller the RPC transport function */
    ServerUserSettingsApi(RpcCaller caller) {
        this.caller = caller;
    }

    /**
     * Lists every known user setting from settings.json, each with its effective value, its default, and whether it is at the default — so settings the user has never set still appear with their default value. Does not include repository- or enterprise-managed overrides that the runtime layers on top at session time.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    public CompletableFuture<UserSettingsGetResult> get() {
        return caller.invoke("user.settings.get", java.util.Map.of(), UserSettingsGetResult.class);
    }

    /**
     * Resolves the on-disk locations of the settings files this runtime reads, so a consumer can show or open them. The user path is the same file `user.settings.get`, `user.settings.load`, and `user.settings.set` operate on. Supply `repoRoot` to also resolve that repository's checked-in and local settings paths. A path is reported whether or not the file exists.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    CompletableFuture<UserSettingsGetPathsResult> getPaths(UserSettingsGetPathsParams params) {
        return caller.invoke("user.settings.getPaths", params, UserSettingsGetPathsResult.class);
    }

    /**
     * Writes one or more user settings to settings.json, replacing each provided top-level key. A key whose value is null is removed.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    public CompletableFuture<Void> set(UserSettingsSetParams params) {
        return caller.invoke("user.settings.set", params, Void.class);
    }

    /**
     * Lists the top-level user-setting keys by who is allowed to govern them: `repo` keys a repository may override through `.github/copilot/settings.json`, `claudeRepo` keys a repository may override through a Claude-compatible config, and `managed` keys an enterprise policy may fix. The lists are static for a given runtime build and fail closed: a key absent from a list cannot be governed that way.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    CompletableFuture<UserSettingsGovernanceKeysResult> listGovernanceKeys() {
        return caller.invoke("user.settings.listGovernanceKeys", java.util.Map.of(), UserSettingsGovernanceKeysResult.class);
    }

    /**
     * Atomically edits one canonical user setting inside its settings-file lock. Reads, canonicalizes touched aliases, applies the edit, validates the resulting top-level setting and writes before releasing the lock. An explicit autoUpdatesChannel set completes the staff migration in the same transaction. Only a malformed existing file returns `invalid`; refused keys, invalid values and I/O errors reject the call.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    CompletableFuture<SettingsEditResult> edit(UserSettingsEditParams params) {
        return caller.invoke("user.settings.edit", params, SettingsEditResult.class);
    }

    /**
     * Rewrites legacy and snake_case setting names in a settings object to their canonical spelling. A caller editing settings.json must normalize before writing: a legacy key left in place survives the write, is re-normalized on the next load, and silently shadows the canonical value the write just stored.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    CompletableFuture<UserSettingsNormalizeResult> normalize(UserSettingsNormalizeParams params) {
        return caller.invoke("user.settings.normalize", params, UserSettingsNormalizeResult.class);
    }

    /**
     * Diagnoses a set of already-loaded settings layers, reporting each key that will not take effect and why. Settings load tolerantly so that one bad key does not stop a session from starting; this method surfaces what was dropped so a caller can report it instead of silently ignoring the user's intent. Takes the layers as given rather than reading them, so a caller can diagnose exactly the content it is showing, including unsaved edits. Never echoes a setting's value, because a value may be a token or another secret. Marked internal because it exists to carry a CLI call site off the native addon, not to serve a consumer-facing need; it becomes public once an SDK consumer needs to inspect settings health itself.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    CompletableFuture<UserSettingsProblemsResult> diagnoseProblems(UserSettingsDiagnoseProblemsParams params) {
        return caller.invoke("user.settings.diagnoseProblems", params, UserSettingsProblemsResult.class);
    }

    /**
     * Validates one top-level setting subtree and writes just that key to settings.json as a single step. Doing both here closes the race a caller cannot close itself: between a separate preflight read and the write, the file may change. Rewriting only the touched key keeps legacy siblings out of settings.json. Answers a status rather than failing, so a validation problem and a write problem stay distinguishable.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    CompletableFuture<UserSettingsApplyTopKeyResult> applyTopKey(UserSettingsApplyTopKeyParams params) {
        return caller.invoke("user.settings.applyTopKey", params, UserSettingsApplyTopKeyResult.class);
    }

    /**
     * Replaces the user settings file's entire content in one write. A key absent from `settings` is removed, which is what distinguishes this from `user.settings.set`: that one merges, so it can never clear a key the caller has dropped. For a caller saving the result of a raw edit, a merge would silently resurrect every key the user deleted. Records the same one-time mutation marks a per-key write records, so a setting the user has now chosen explicitly stops being migrated for them.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    CompletableFuture<Void> writeAll(UserSettingsWriteAllParams params) {
        return caller.invoke("user.settings.writeAll", params, Void.class);
    }

    /**
     * Merges a partial object into one top-level user setting, re-reading the stored value inside this runtime process's settings.json write lock so that concurrent writers in this process to different sub-fields of the same key do not clobber each other. Unlike `user.settings.set`, which replaces a key's whole value with what the caller last read, this keeps sub-fields the caller did not name when both the stored value and supplied value are objects; otherwise the supplied value replaces the stored value. Use it whenever two independent surfaces own different sub-fields of one setting. Returns the key's stored value after the write, in `value`.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    CompletableFuture<UserSettingsUpdateResult> update(UserSettingsUpdateParams params) {
        return caller.invoke("user.settings.update", params, UserSettingsUpdateResult.class);
    }

    /**
     * Reads settings.json as stored for a caller that is about to edit it. Unlike `user.settings.get`, this returns file values rather than per-key metadata and defaults. Reports `missing` for an absent file and `invalid` when the existing file will not parse. Retired config.json and extensionless files are not read.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    CompletableFuture<UserSettingsLoadForEditResult> loadForEdit() {
        return caller.invoke("user.settings.loadForEdit", java.util.Map.of(), UserSettingsLoadForEditResult.class);
    }

    /**
     * Validates one top-level setting subtree without writing it, reporting each problem with the path it applies to. Scoped to a single top-level key on purpose: validating the whole file would reject an edit whenever an unrelated pre-existing field is malformed, locking the user out of the very tool that could fix it.
     *
     * @apiNote This method is experimental and may change in a future version.
     * @since 1.0.0
     */
    @CopilotExperimental
    CompletableFuture<UserSettingsValidateResult> validate(UserSettingsValidateParams params) {
        return caller.invoke("user.settings.validate", params, UserSettingsValidateResult.class);
    }

}
