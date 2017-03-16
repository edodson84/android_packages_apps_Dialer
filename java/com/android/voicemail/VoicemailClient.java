/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.voicemail;

import android.content.Context;
import android.provider.VoicemailContract.Voicemails;
import android.support.annotation.Nullable;
import android.telecom.PhoneAccountHandle;
import android.telephony.TelephonyManager;
import java.util.List;

/** Public interface for the voicemail module */
public interface VoicemailClient {

  /**
   * Broadcast to tell the client to upload local database changes to the server. Since the dialer
   * UI and the client are in the same package, the {@link
   * android.content.Intent#ACTION_PROVIDER_CHANGED} will always be a self-change even if the UI is
   * external to the client.
   */
  String ACTION_UPLOAD = "com.android.voicemailomtp.VoicemailClient.ACTION_UPLOAD";

  /**
   * Appends the selection to ignore voicemails from non-active OMTP voicemail package. In OC there
   * can be multiple packages handling OMTP voicemails which represents the same source of truth.
   * These packages should mark their voicemails as {@link Voicemails#IS_OMTP_VOICEMAIL} and only
   * the voicemails from {@link TelephonyManager#getVisualVoicemailPackageName()} should be shown.
   * For example, the user synced voicemails with DialerA, and then switched to DialerB, voicemails
   * from DialerA should be ignored as they are no longer current. Voicemails from {@link
   * #OMTP_VOICEMAIL_BLACKLIST} will also be ignored as they are voicemail source only valid pre-OC.
   */
  void appendOmtpVoicemailSelectionClause(
      Context context, StringBuilder where, List<String> selectionArgs);
  /**
   * @return the class name of the {@link android.preference.PreferenceFragment} for voicemail
   *     settings, or {@code null} if dialer cannot control voicemail settings. Always return {@code
   *     null} before OC.
   */
  @Nullable
  String getSettingsFragment();

  boolean isVoicemailArchiveEnabled(Context context, PhoneAccountHandle phoneAccountHandle);

  void setVoicemailArchiveEnabled(
      Context context, PhoneAccountHandle phoneAccountHandle, boolean value);
}