package edu.stanford.slac.smb.samples;

import ssrl.beans.AppSession;
import ssrl.beans.AuthSession
import ssrl.beans.AppSession
import ssrl.beans.AppSessionBase

class CachedSessionIdGrabber {
	static public AppSession grabSession() {
		
		def env = System.getenv();
		def user = env['USER'];
		
		File sessionIdFile = new File("/home/${user}/.bluice/session");
		//println sessionIdFile.text
		AppSession appSession = new AppSessionBase(authSession: new AuthSession(userName: user, sessionId:sessionIdFile.text.trim()) );
		return appSession;
	}
}
